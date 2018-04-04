package org.ua.oblik.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    private final JwtAuthenticationEntryPoint unauthorizedHandler;
//    private final UserDetailsService userDetailsService;
//    private final int MAX_AGE = 3600;

//    @Autowired
//    public SecurityConfiguration(JwtAuthenticationEntryPoint unauthorizedHandler, UserDetailsService userDetailsService) {
//        this.unauthorizedHandler = unauthorizedHandler;
//        this.userDetailsService = userDetailsService;
//    }

//    @Autowired
//    public void configureAuthentication(AuthenticationManagerBuilder builder) throws Exception {
//        builder.userDetailsService(this.userDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }
//
//    @Value("${app.origin.url}")
//    private String url;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
//        return new JwtAuthenticationFilter();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .antMatchers("/api/private/**").hasRole("USER")
//                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll();
//        http
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        http
//                .headers().cacheControl();
    }

//    @Bean
//    public WebMvcConfigurer CORSConfig() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins(url)
//                        .allowCredentials(true)
//                        .allowedHeaders("Access-Control-Allow-Credentials", "Content-Type", "Access-Control-Allow-Headers", "X-Requested-With", "Origin", "Accept")
//                        .allowedMethods("PUT", "DELETE", "GET", "POST")
//                        .maxAge(MAX_AGE);
//            }
//        };
//    }


//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("OPTIONS");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("DELETE");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

//    @Bean
//    public ErrorAttributes errorAttributes() {
//        return new DefaultErrorAttributes() {
//            @Override
//            public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
//                Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
//                Throwable error = getError(requestAttributes);
//                if (error instanceof BadCredentialsException) {
//                    errorAttributes.remove("timestamp");
//                    errorAttributes.remove("exception");
//                    errorAttributes.remove("path");
//                }
//                return errorAttributes;
//            }
//        };
//    }
}
