
package com.myl.springsockets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;


@Configuration
@EnableWebMvc
@EnableWebSocket
@ComponentScan(basePackages={"com.myl.springsockets"})
public class WebConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(chatWebSocketHandler(), "/lobbyws").addHandler(duelWebSocketHandler(), "/chatws");
//    registry.addHandler(chatWebSocketHandler(), "/lobbyws").setHandshakeHandler(handshakeHandler()).addHandler(duelWebSocketHandler(), "/chatws").setHandshakeHandler(handshakeHandler());
  }
  
  @Bean
  public WebSocketHandler chatWebSocketHandler() {
    return new PerConnectionWebSocketHandler(ChatWebSocketHandler.class);
  }
  
  @Bean
  public WebSocketHandler duelWebSocketHandler() {
    return new PerConnectionWebSocketHandler(DuelWebSocketHandler.class);
  }

  @Bean
  public ServletServerContainerFactoryBean createWebSocketContainer() {
      ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
      container.setMaxTextMessageBufferSize(30*1024);
      container.setMaxSessionIdleTimeout(1*600000);
      return container;
  }
  
//  @Bean
//  public DefaultHandshakeHandler handshakeHandler() {	  
//      WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
//      policy.setInputBufferSize(20*1024);      
//
//      return new DefaultHandshakeHandler(
//              new JettyRequestUpgradeStrategy(new WebSocketServerFactory(policy)));
//  }

  // Allow serving HTML files through the default Servlet
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
          configurer.enable();          
  }


}
