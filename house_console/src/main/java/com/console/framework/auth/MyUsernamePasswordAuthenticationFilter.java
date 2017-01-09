package com.console.framework.auth;   
   
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.console.entity.TSysUser;
import com.console.framework.dao.MyBatisDao;
import com.console.util.Constant;
import com.console.util.MD5;
import com.console.util.MD5Encoder;
import com.console.util.StringUtil;

   
   
/*  
 *   
 * UsernamePasswordAuthenticationFilter源码  
    attemptAuthentication  
        this.getAuthenticationManager()  
            ProviderManager.java  
                authenticate(UsernamePasswordAuthenticationToken authRequest)  
                    AbstractUserDetailsAuthenticationProvider.java  
                        authenticate(Authentication authentication)  
                            P155 user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);  
                                DaoAuthenticationProvider.java  
                                    P86 loadUserByUsername  
 */   

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter  {
	//public static Logger logger = Logger.getLogger(MyUsernamePasswordAuthenticationFilter.class);
	//UserInfoManager userInfoManager = ContextLoader.getCurrentWebApplicationContext().getBean(UserInfoManager.class);
    //public static final String VALIDATE_CODE = "j_validatecode";   
    public static final String USERNAME = "j_username";   
    public static final String PASSWORD = "j_password";
    public static final String REMBERME = "j_remberme";
    public static final String VALIDATE_CODE = "j_validatecode";
    public static final String LOCALVAL = "session_localval";
    
    private MyBatisDao myBatisDao;
	public MyBatisDao getMyBatisDao() {
		return myBatisDao;
	}
	public void setMyBatisDao(MyBatisDao myBatisDao) {
		this.myBatisDao = myBatisDao;
	}
	
	//@Autowired
	//private ShardedJedisPool shardedjedispool;
   
    @Override   
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException { 
//        if (!request.getMethod().equals("POST")) {   
//            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());   
//        }   
    	//检测验证码    
		checkValidateCode(request);   
        
		String localval = getLocalval(request);
		
		String username = obtainUsername(request);   
		String password = obtainPassword(request);   
	    
        Map<String,Object> usermap = new HashMap<String, Object>();
        if(username == null || "".equals(username) || password == null || "".equals(password)) {
        	throw new AuthenticationServiceException("用户名或密码错误");
        }else{
        	if(username.length()>=1){
    			List<TSysUser> userlist = myBatisDao.getList("userMapper.getUserByUserName", username);
    			if(userlist != null && userlist.size()==1){
    				TSysUser sysUser = userlist.get(0);
    				password = MD5.md5(password);
    				if(sysUser.getPassword() == null){
    					throw new AuthenticationServiceException("用户名或密码错误");
    				}else if(sysUser.getPassword() != null 
    						&& !sysUser.getPassword().equals(MD5Encoder.encode(password))){
    					throw new AuthenticationServiceException("用户名或密码错误");
    				}else{
    					// 用户被锁定
    					if (sysUser.getStatus().equals(Constant.LOCKED_STATUS)) {
    						throw new AuthenticationServiceException("账户已锁定，请联系管理员！");
    					}else{
    						request.getSession().setAttribute(Constant.SESSION_USERINFO, sysUser);
    					}
    				}
    			}else if(userlist.size()>1){
    				throw new AuthenticationServiceException("账户异常，请联系管理员！");
    			}else{
    				throw new AuthenticationServiceException("用户名或密码错误");
    			}
        	}else{
        		throw new AuthenticationServiceException("用户名或密码错误");
        	}
        }
         
        //UsernamePasswordAuthenticationToken实现 Authentication   
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // Place the last username attempted into HttpSession for views    
           
        // 允许子类设置详细属性    
        setDetails(request, authRequest);   
           
        // 运行UserDetailsService的loadUserByUsername 再次封装Authentication    
        return this.getAuthenticationManager().authenticate(authRequest);   
    }   
    
    protected void checkValidateCode(HttpServletRequest request) {    
        HttpSession session = request.getSession();    
        String sessionValidateCode = (String)session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY); 
        String validateCodeParameter = obtainValidateCodeParameter(request);   
        if (StringUtil.isEmpty(validateCodeParameter) || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {
        	throw new AuthenticationServiceException("验证码错误！"); 
        }     
    }
    
    /**
     * 获得SESSION的语言栏
     * @param request
     * @return
     */
    protected String getLocalval(HttpServletRequest request){
    	HttpSession session = request.getSession();
    	String localval = obtainSessionLocalVal(session);
    	if(localval == null || localval.equals("") || localval.equals("null")){
    		localval = "zh_CN";
    	}
    	return localval;
    }
       
    @SuppressWarnings("unused")
	private String obtainValidateCodeParameter(HttpServletRequest request) {   
        Object obj = request.getParameter(VALIDATE_CODE);   
        return null == obj ? "" : obj.toString();   
    }   
   
    protected String obtainSessionValidateCode(HttpSession session) {   
        Object obj = session.getAttribute(VALIDATE_CODE);   
        return null == obj ? "" : obj.toString();   
    }
    
    protected String obtainSessionLocalVal(HttpSession session){
    	Object obj = session.getAttribute(LOCALVAL);   
        return null == obj ? "" : obj.toString();  
    }

    protected String obtainUsername(HttpServletRequest request) {   
        Object obj = request.getParameter(USERNAME);   
        return null == obj ? "" : obj.toString();   
    }   

    protected String obtainPassword(HttpServletRequest request) {   
        Object obj = request.getParameter(PASSWORD);   
        return null == obj ? "" : obj.toString();   
    } 
    
    protected String obtainRemberme(HttpServletRequest request) {   
        Object obj = request.getParameter(REMBERME);   
        return null == obj ? "" : obj.toString();   
    }
    
    public static void main(String[] args) {
		System.err.println(MD5.md5("admin"));
		System.err.println(MD5Encoder.encode(MD5.md5("admin")));
	}
       
}  
