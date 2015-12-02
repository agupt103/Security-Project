package authentication;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private String captcha;
	private String otpCode;
	private String OTPOption;
	//Sayantan
	private String ssn;
	private String accounttype;
	private String usertype;
	private String firstname;
	private String lastname;
    private String address;
	private String email1;
	private String phonenumber;



	/* Spring Security fields*/
	private List<Role> authorities;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;

	public User(){}

	public User(String username, String password, String email1, String firstname, String lastname){
		this.username = username;
		this.password = password;
		this.email1 = email1;
		this.firstName = firstname;
		this.lastName = lastname;
	}
   //Sayantan Guha: Constructor override for generating of the external user review
	public User(String ssn, String accounttype, String usertype,String firstname,String lastname,String email1,String phonenumber ,String address){
		this.ssn=ssn;
		this.accounttype=accounttype;
		this.usertype=usertype;
		this.firstname=firstname;
		this.lastname=lastname;
        this.address = address;
		this.email1=email1;
		this.phonenumber=phonenumber;

	}
	public String getssn() {
		return ssn;
	}
	public String getaddress(){
		return address;
	}
	public String getaccounttype() {
		return accounttype;
	}
	public String getusertype() {
		return usertype;
	}
	public String getfirstname() {
		return firstname;
	}
	public String getlastname() {
		return lastname;
	}
	public String getemail1() {
		return email1;
	}
	public String getphonenumber() {
		return phonenumber;
	}
	public void setfirstname(String firstname) {
		this.firstname = firstname;
	}
	public void setlastname(String lastname) {
		this.lastname = lastname;
	}
	public void setusertype(String usertype){
		this.usertype = usertype;
	}
	public void setaccounttype(String accounttype){
		this.accounttype = accounttype;
	}
	public void setssn(String ssn){
		this.ssn=ssn;
	}
	public void setaddress(String address){
		this.address = address;
	}
	public void setemail1(String email){
		this.email1 = email;
	}
	public void setphonenumber(String phonenumber){
		this.phonenumber = phonenumber;
	}
	//Sayantan: Copy upto this

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public String getOTPOption() {
		return OTPOption;
	}

	public void setOTPOption(String oTPOption) {
		OTPOption = oTPOption;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(List<Role> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", captcha=" + captcha
				+ ", otpCode=" + otpCode + ", authorities=" + authorities
				+ ", accountNonExpired=" + accountNonExpired
				+ ", accountNonLocked=" + accountNonLocked
				+ ", credentialsNonExpired=" + credentialsNonExpired
				+ ", enabled=" + enabled + "]";
	}


}
