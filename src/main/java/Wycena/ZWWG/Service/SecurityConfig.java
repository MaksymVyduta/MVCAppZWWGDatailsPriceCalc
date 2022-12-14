package Wycena.ZWWG.Service;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailMy userDetailsService ;
    public SecurityConfig (UserDetailMy userDetailsService) {
        this.userDetailsService = userDetailsService ;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( userDetailsService );
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().disable();
        http.authorizeRequests()
                .antMatchers("/admindane", "/newuser" ,"/mieszanki","/nowamieszanka","/wklady","/obrot").hasRole("ADMIN")
               .antMatchers("/wycena", "/oringwycena","/simmerwycena","/Uwycena","/Zwycena", "/ZZwycena").hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin().defaultSuccessUrl("/admindane");

    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
