package org.springboot.storelocator.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enable @PreAuthorize method-level security
@ConditionalOnProperty(name = "store.authentication.enabled", havingValue = "true")
public class StoreAuthenticationConfig extends WebSecurityConfigurerAdapter {

    
	
    @Autowired
	UserDetailsService userDetailsService;
    
    @Value("${store.authentication.auth-token}")
    String validAuthKey;
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
    	  ApiKeyAuthenticationFilter filter = new ApiKeyAuthenticationFilter("api-key");
          filter.setAuthenticationManager(new AuthenticationManager() {

              @Override
              public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                  String principal = (String) authentication.getPrincipal();
                  if (!validAuthKey.equals(principal))
                  {
                      throw new BadCredentialsException("The API key was not found or not the expected value.");
                  }
                  authentication.setAuthenticated(true);
                  return authentication;
              }
          });
          http.
              antMatcher("/**").
              csrf().disable().
              sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
              and().addFilter(filter).authorizeRequests().anyRequest().authenticated();
			/*
			 * http .authorizeRequests() .anyRequest() .authenticated() .and() .httpBasic()
			 * .and() .csrf() .disable();
			 */
        // @formatter:on
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth.userDetailsService(userDetailsService);
        // @formatter:on
    }
}
