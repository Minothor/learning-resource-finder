
package learningresourcefinder.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import learningresourcefinder.mail.MailingDelayType;
import learningresourcefinder.security.Privilege;
import learningresourcefinder.util.HTMLUtil;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.jsoup.helper.StringUtil;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements Cloneable, Comparable<User>, Serializable {
   
    private static final long serialVersionUID = 4144665927166518905L;

    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
    //this is the MD5 print of the universal password
    public static final String UNIVERSAL_PASSWORD_MD5 = "477bc098b8f2606137c290f9344dcee8";
    public static final String UNIVERSAL_DEV_PASSWORD_MD5 = "e77989ed21758e78331b20e477fc5582";  // "dev" in clear. -> any developer can use "dev" to impersonate anybody when developing. Does not work in production. 

    
    public enum UserType{
    	KID("Enfant"), ADULT("Adulte");
    	String description;    	
    	
    	private UserType(String description) {
			this.description = description;
		}
  
		public String getDescription() {
			return description;
		}
	}
    
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.ADULT ;

    
    
    public enum Level{
    	
    	LEVEL_1(1,30),LEVEL_2(2,100),LEVEL_3(3,200),LEVEL_4(4,300),LEVEL_5(5,400),LEVEL_6(6,500);
    	
    	int levelProgressPoints;
    	int levelIndex;
    	
    	Level(int levelIndex, int levelProgressPoints){
    		this.levelProgressPoints = levelProgressPoints;
    		this.levelIndex = levelIndex;
    	}

		public int getLevelProgressPoints() {
			return levelProgressPoints;
		}

		public int getLevelIndex() {
			return levelIndex;
		}
	
		public static int getHighestLevelIndex() {
			return Level.values().length;
		}
		
    }
    
    @Column(nullable=false)
    @Enumerated(EnumType.ORDINAL)
    private Level accountLevel = Level.LEVEL_1;
    
	public Level getAccountLevel() {
		return accountLevel;
	}

	public void setAccountLevel(int levelIndex) {
		this.accountLevel = Level.values()[levelIndex-1];
	}
	
	@Column(nullable=false)
	private int userProgressPoints;
	
	public int getUserProgressPoints() {
		return userProgressPoints;
	}

	public void setUserProgressPoints(int userProgressPoints) {
		this.userProgressPoints = userProgressPoints;
	}

	/**
     * Status for an account :<br />
     * LOCKED: Account manually locked by an admin, normally a lock reason
     * should be available.<br />
     * NOTVALIDATED: The account exists but is not yet usable as the email
     * address has not been validated.<br />
     * ACTIVE: Account with valid email address.<br />
     */
    public enum AccountStatus {
        LOCKED("Locked"),
        NOTVALIDATED("Mail not validated yet"),     
        ACTIVE( "Active");

        String name;

        AccountStatus(String aName) {
            this.name = aName;
        }
        public String getName() {
            return name;
        }
    }


    public enum Gender {
        FEMALE("Femme"), MALE("Homme");
        String description;

        private Gender(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
        
        
    }
    
    public boolean isFemale(){
        return this.gender==Gender.FEMALE;
    }

    public enum Role {
        ADMIN("Administrator", 10),
        SUBADMIN("Sub-Administrator", 15),
        MODERATOR("Moderator", 100),
        CORRECTOR("Corrector",500),
        USER("User", 1000);

        private int level;
        String name;

        private Role(String aName, int level) {
            this.level = level;
            this.name = aName;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }
        
        /**
         * Test if the current role is lower or equivalent than the role given as parameter.<br/>
         * Note that you test role and not its value.
         *
         * @param level
         * to test against this <tt>CommunityRole</tt>.
         * @return true or false.
         */
        public boolean isLowerOrEquivalent(Role level) {
            if (level == null)
                return this == Role.USER;
            return this.getLevel() >= level.getLevel();
        }

        /**
         * Test if the current role is higher or equivalent than the role given as parameter.<br/>
         * Note that you test role and not its value.
         *
         * @param level
         * to test against this <tt>CommunityRole</tt>.
         * @return true or false.
         */
        public boolean isHigherOrEquivalent(Role level) {
            if (level == null)
                return this == Role.USER;
            return this.getLevel() <= level.getLevel();
        }

    }
    
    public enum AccountConnectedType{
        LOCAL("User connected with local account","local"),
        FACEBOOK("User connected with his Facebook account","facebook"),
        GOOGLE("User connected with his google account","googleplus");


        private String detail;
        private String providerId;  // Used in spring social to identify the provider.
        private AccountConnectedType(String detail,String providerId){
            this.detail = detail;
            this.providerId = providerId;
        }

        public String getDetail(){
            return detail;
        }
        public String getProviderId(){
            return providerId;
        }
        public static AccountConnectedType getProviderType(String providerId){
            providerId = providerId.toLowerCase();
            switch(providerId){
            case "facebook" : return AccountConnectedType.FACEBOOK;
            case "googleplus"   : return AccountConnectedType.GOOGLE;
            default : throw new RuntimeException("Provider cannot be identified");
            }
        }

    }
 
   
    @Enumerated(EnumType.STRING)
    private AccountConnectedType accountConnectedType;  // Last login with facebook, google or direct ?
    
    @Column(length = 50)
    @Size(max=50, message="votre prénom ne peut contenir que 50 caractères maximum")
    private String firstName;// in case of a special user we use the firstname to display the name of the association.
    
    @Column(length = 50)
    @Size(max=50, message="votre nom ne peut contenir que 50 caractères maximum")
    private String lastName;

    @Column(unique = true, nullable=false)
    @Size(max = 15, message = "votre pseudonyme ne peut contenir que 15 caractères maximum")
    @NotBlank(message="entrer votre pseudonyme")
    @Pattern(message ="ne peut contenir que des caractère alphanumériques, sans accents. Les 2 caractères \"-\" et \"_\" sont autorisés, mais pas les espaces.", regexp="[A-Za-z0-9_-]{2,256}")
    private String userName; 
    
    @Column(length = 100)
    @Email(message ="adresse mail pas valide", regexp="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    private String mail;
    
    private String title;
    
    @Column(length = 100)
    @Size(min = 4, message = "votre mot de passe doit contenir au moins 4 caractères") // TODO: verify if we need to remove this line and check the size manually in the controller (register and change pwd). John 2013-08-20
    private String password; // MD5 encoded password. May be null if user registered via facebook/google and never logedin directly.

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    //FIXME waarom ? --28/11/12
    // @Temporal(TemporalType.TIMESTAMP)
    //  @NotNull(message="you must indicate your birthdate in format yyyy-MM-dd")
    private Date birthDate;

    private boolean picture;  // Picture name = user id + ".jpg"

    private Date lastMailSentDate;

    @ElementCollection(targetClass=Privilege.class, fetch=FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @JoinTable(name="users_privileges")
    @Column(name="privilege", nullable=false,length = 50)
    private Set<Privilege> privileges = new HashSet<Privilege>();

    private Date lastAccess;

        
    @Column(length = 40)
    private String lastLoginIp;

    private int consecutiveFailedLogins = 0; // Reset to 0 every time users logins sucessfully.
    private Date lastFailedLoginDate; // Null if consecutiveFailedLogins == 0

    private String validationCode;  // sent to the user with the validation mail at registration. 

    private String lockReason;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private boolean nlSubscriber = true; 

    private boolean spammer = false;

    @ManyToOne
    private User spamReporter;
   
    
    @Enumerated(EnumType.STRING)
    private MailingDelayType mailingDelayType = MailingDelayType.DAILY; 
    public MailingDelayType getMailingDelayType() {
        return this.mailingDelayType;
    }

   
    private Role role = Role.USER;

    @ManyToMany
    private Set<School> schools = new HashSet<>();
    
    @OneToMany (mappedBy="createdBy")
    Set <PlayList> playListList = new HashSet<>();
    
    
    
    /////////////////////////////////////////: GETTERS & SETTERS //////////////////////////
    /////////////////////////////////////////: GETTERS & SETTERS //////////////////////////
    /////////////////////////////////////////: GETTERS & SETTERS //////////////////////////
    /////////////////////////////////////////: GETTERS & SETTERS //////////////////////////
    /////////////////////////////////////////: GETTERS & SETTERS //////////////////////////
    
    
    

    
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = HTMLUtil.removeHtmlTags(title);
	}
    
    public Role getRole() {
        return role;
    }

    public AccountConnectedType getAccountConnectedType() {
        return accountConnectedType;
    }

    public void setAccountConnectedType(AccountConnectedType accountConnectedType) {
        this.accountConnectedType = accountConnectedType;
    }

    public void setRole(Role roleParam) {
        this.role = roleParam;
    }

    public boolean hasAdminPrivileges() {
        return role == Role.ADMIN;
    }

    public boolean hasModeratorPrivileges() {
        return role == Role.ADMIN
                || role == Role.MODERATOR;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = HTMLUtil.removeHtmlTags(firstName);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = HTMLUtil.removeHtmlTags(userName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = HTMLUtil.removeHtmlTags(lastName);
    }

    public Date getLastMailSentDate() {
        return lastMailSentDate;
    }

    public void setLastMailSentDate(Date lastMailSendedDate) {
        this.lastMailSentDate = lastMailSendedDate;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail != null ? HTMLUtil.removeHtmlTags(mail.toLowerCase().trim()) : null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password != null ? password.toLowerCase() : password;
    }
    
    public boolean isKid() {
		return userType.equals(UserType.KID);
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public String getLockReason() {
        return lockReason;
    }

    public void setLockReason(String lockReason) {
        this.lockReason = HTMLUtil.removeHtmlTags(lockReason);
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }


    public boolean isPicture() {
        return picture;
    }

    public void setPicture(boolean picture) {
        this.picture = picture;
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFullName() {
        String lnChar = (lastName == null || lastName.isEmpty()) ? "" : " "
                + StringUtils.capitalize(lastName);
        String firstAndLastName = (firstName == null || firstName.isEmpty()) ? "" : " "
                + StringUtils.capitalize(firstName)
                + lnChar;
        
        if (!StringUtil.isBlank(firstAndLastName)  ) {
            return firstAndLastName;
        } else {
            return this.getUserName();
        }
    }

    public boolean isNlSubscriber() {
        return nlSubscriber;
    }

    public void setNlSubscriber(boolean nlSubscriber) {
        this.nlSubscriber = nlSubscriber;
    }

    public int compareTo(User o) {
        return this.getLastName().compareTo(o.getLastName());
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public boolean isSpammer() {
        return spammer;
    }

    public void setSpammer(boolean spammer) {
        this.spammer = spammer;
    }

    public User getSpamReporter() {
        return spamReporter;
    }

    public void setSpamReporter(User spamReporter) {
        this.spamReporter = spamReporter;
    }

    public int getConsecutiveFailedLogins() {
        return consecutiveFailedLogins;
    }

    public void setConsecutiveFailedLogins(int consecutiveFailedLogins) {
        this.consecutiveFailedLogins = consecutiveFailedLogins;
    }

    public Date getLastFailedLoginDate() {
        return lastFailedLoginDate;
    }

    public void setLastFailedLoginDate(Date lastFailedLoginDate) {
        this.lastFailedLoginDate = lastFailedLoginDate;
    }

    public void setMailingDelayType(MailingDelayType mailingDelay) {
        this.mailingDelayType = mailingDelay;
    }

	public Set<School> getSchools() {
		return schools;
	}

	public Set<PlayList> getPlayListList() {
		return playListList;
	}  
    
    @Override
    public Long getId() {
        return id;
    }
    
    public UserType getUserType() {
        return userType;
    }
   
    
}
