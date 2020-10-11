package com.umbrella.leancloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class MyGateWayGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("********进入全局网关过滤器*********");
        String name = exchange.getRequest().getQueryParams().getFirst("username");
        if(name == null){
            log.info("***********请求参数中没有username，过滤器不通过***********");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);//没有username参数 就不通过
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
