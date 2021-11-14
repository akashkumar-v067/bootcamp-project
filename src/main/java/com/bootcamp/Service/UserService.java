package com.bootcamp.Service;

import com.bootcamp.Dao.UserDao;
import com.bootcamp.Dto.UseDto.AddressDto;
import com.bootcamp.Entities.User.*;
import com.bootcamp.Exceptions.*;
import com.bootcamp.Repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Primary
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenRepository tokenRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private SellerRepository sellerRepo;
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    AddressRepository addressRepo;
    @Autowired
    CurrentUserService currentUserService;


    public boolean registerAsAdmin(User user) {
        if (userRepo.checkEmail(user.getEmail())) {
            throw new EmailExistsException(user.getEmail() + " already Exist");
        }

        if (!(user.getPassword().equals(user.getRetypePassword()))) {
            throw new BadRequestException("password and confirm password should be same");
        }
        String encodedPassword = encoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        user.setRole(Arrays.asList(new Role("ROLE_ADMIN")));
        user.setActive(true);
        userRepo.save(user);
        return true;
    }


    public boolean registerAsSeller(Seller seller) {
        if (userRepo.checkEmail(seller.getEmail())) {
            throw new EmailExistsException(seller.getEmail() + " already Exist");
        }
        if (!(seller.getPassword().equals(seller.getRetypePassword()))) {
            throw new BadRequestException("password and confirm password should be same");
        }
        String encodedPassword = encoder.encode(seller.getPassword());
        seller.setPassword(encodedPassword);
        seller.setRole(Arrays.asList(new Role("ROLE_SELLER")));
        userRepo.save(seller);
        emailService.sendSimpleMessage(seller.getEmail(), "Account Registered ", "your account is registered as seller." +
                " \n" + "Waiting for approval from admin");

        return true;

    }



    public boolean registerCustomer(Customer customer){
        if (userRepo.checkEmail(customer.getEmail())) {
            throw new EmailExistsException(customer.getEmail() + " already Exist");
        }
        if (!(customer.getPassword().equals(customer.getRetypePassword()))) {
            throw new BadRequestException("password and confirm password should be same");
        }
        String encodedPassword = encoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        customer.setRole(Arrays.asList(new Role("ROLE_CUSTOMER")));
        customerRepo.save(customer);
        Token t = tokenService.createToken(customer.getEmail());
        emailService.sendSimpleMessage(customer.getEmail(), "link to activate  account ", "your email is registered. click the link below to activate account\n\n" +
                "http://localhost:8080/app/activation/" + customer.getEmail() + "/?token=" + t.getToken());

        return true;
    }







    public boolean activateUser(String email,String token){
        if(tokenService.verifyToken(token, email)){
          userRepo.activateUser(email);
          tokenRepo.deleteToken(email);
          emailService.sendSimpleMessage(email,"Account Activation Confirmation","Your acoount successfully Activated with " +
                  "email: "+email+"\n"+"Thanks for choosing us");
           return true;

       }
       else
           return false;
   }

   public String forgotPassword(String email){
        if(userRepo.checkEmail(email)){
           Token t= tokenService.createForgotPassToken(email);
           emailService.sendSimpleMessage(email,"Link to Reset Password",
                   "http://localhost:8080/app/resetpassword?email="+email+"&token="+t.getForgotPassToken());
           return "reset password link send successfully";
        }
        else
        {
            throw new NotFoundException("email does not exist");
        }

   }

   public String resetPassword(String email,String token,String password,String confirmPasword){
        if(password.equals(confirmPasword)) {
            if (tokenService.verifyForgotPassToken(token, email)) {
                String encodedPassword = encoder.encode(password);
                tokenRepo.deleteForgotPassToken(email);
                userRepo.resetPassword(encodedPassword, email);
                return "password updated successfully";
            } else {
                throw new TokenNotFoundException("Invalid Token");
            }
        }
        else
            throw new BadRequestException("password and confirm password should be same");
   }

    public void resendActivationLink(String email){
        if(userRepo.checkEmail(email)){
            User u=userRepo.findByEmail(email);
            if(u.getActive()){
                throw new EmailAlreadyActiveException("Account is already active");
            }
            else{
                Token t=tokenService.updateToken(email);
                emailService.sendSimpleMessage(email,"Activation link","your email is registered. click the link below to activate account\n  " +
                        "http://localhost:8080/app/activation/" + email + "/?token=" + t.getToken());
            }
        }
        else
            throw new NotFoundException("email does not exist");
    }

    public String updatePassword(String password,String confirmPassword){
        if(password.equals(confirmPassword)) {
            String email = currentUserService.getUser();
            String encodedPassword = encoder.encode(password);
            userRepo.resetPassword(encodedPassword, email);
            return "password updated successfully";

        }
        else
            throw new BadRequestException("password and confirm password should be same");
    }


    public String addAddress(AddressDto addressDto){
        String email=currentUserService.getUser();
        User user=userRepo.findByEmail(email);
        Address address=toAddress(addressDto);
        address.setUser(user);
        user.addAddress(address);
        userRepo.save(user);
        return "Address saved";
    }


    @Modifying
    @Transactional
    public String deleteAddress(Long id) {
        String username=currentUserService.getUser();
        Optional<Address> addressOptional=addressRepo.findById(id);
        if(!addressOptional.isPresent()){
            throw new NotFoundException("address not present");
        }
        Address savedAddress = addressOptional.get();
        if(savedAddress.getUser().equals(username)){
            addressRepo.deleteAddressById(id);
            return "address deleted";
        }
        return "profile is updated";
    }


    public List<AddressDto> getAddress(){
        Long[] l = {};
        String email = currentUserService.getUser();
        Customer customer = customerRepo.findByEmail(email);
        Set<Address> addresses = customer.getAddresses();
        List<AddressDto> list = new ArrayList<>();
        if (addresses.isEmpty())
        {
            throw new NotFoundException("Address not found");
        }
        else
        {
            for (Address address : addresses)
            {
                AddressDto addressDTO = modelMapper.map(address,AddressDto.class);
                list.add(addressDTO);

            }

        }
        return list;
    }


    @Modifying
    @Transactional
    public void updateAddress(Long id, AddressDto address) {
        String email=currentUserService.getUser();
        User user=userRepo.findByEmail(email);
        Set<Address> addresses=user.getAddresses();
        Optional<Address> address1 = addressRepo.findById(id);
        int count=0;
        if(!address1.isPresent()){
            throw new NotFoundException("Address not found");
        }
        else{
            Address savedAddress2 = address1.get();
            for (Address address2 : addresses) {
                if (address1.get().getId() == address2.getId()) {
                    if (address.getAddressLine() != null)
                        address2.setAddressLine(address.getAddressLine());
                    if (address.getCity() != null)
                        address2.setCity(address.getCity());
                    if (address.getCountry() != null)
                        address2.setCountry(address.getCountry());
                    if (address.getState() != null)
                        address2.setState(address.getState());
                    if (address.getZipCode() != null)
                        address2.setZipCode(address.getZipCode().toString());
                    address2.setUser(user);
                    address2.setId(id);
                    addressRepo.save(address2);
                    count++;
                }
            }
            if (count==0)
            {
                throw new NullException("you cannot update this address");
            }
        }
    }


    public String logout(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return "Logged out successfully";
    }


    public Address toAddress(AddressDto addressDto){
        if(addressDto != null)
            return modelMapper.map(addressDto, Address.class);
        return null;
    }

    public AddressDto toAddressDto(Address address){
        if(address != null)
            return modelMapper.map(address, AddressDto.class);
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u=userRepo.findByEmail(username);
        return new UserDao(u);
    }
}
