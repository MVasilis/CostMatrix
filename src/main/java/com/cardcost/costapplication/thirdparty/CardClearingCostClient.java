package com.cardcost.costapplication.thirdparty;

import com.cardcost.costapplication.config.yamlProperties;
import com.cardcost.costapplication.service.exception.ThirdPartyException;
import com.cardcost.costapplication.thirdparty.response.BinListClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static com.cardcost.costapplication.common.Constant.BACK_SLASH;

@Slf4j
@Service
public class CardClearingCostClient {


    @Autowired
    private yamlProperties properties;

    public Mono<BinListClientResponse> retrieveBisListData(String issuerIdentityNumber){
        try {
            return createdWebClient(properties.getBinListUrl()).get()
                    .uri(BACK_SLASH + issuerIdentityNumber)
                    .retrieve()
                    .bodyToMono(BinListClientResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error communicating with bin list service " + Arrays.toString(e.getStackTrace()));
            throw new ThirdPartyException(e, "Error communicating with bin list service ");
        }
    }

    private WebClient createdWebClient(String url){
        return WebClient.create(url);
    }

}
