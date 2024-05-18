package cn.glfs.ltzf;

import cn.glfs.ltzf.factory.Configuration;
import cn.glfs.ltzf.factory.PayFactory;
import cn.glfs.ltzf.factory.defaults.DefaultPayFactory;
import cn.glfs.ltzf.payments.h5.H5PayService;
import cn.glfs.ltzf.payments.nativepay.NativePayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(LtzfSDKConfigProperties.class)
public class LtzfSDKConfig {

    @Bean(name = "payFactory")
    @ConditionalOnProperty(value = "ltzf.sdk.config.enabled", havingValue = "true", matchIfMissing = false)
    public PayFactory payFactory(LtzfSDKConfigProperties properties) {
        Configuration configuration = new Configuration(
                properties.getAppId(),
                properties.getMerchantId(),
                properties.getPartnerKey()
        );

        return new DefaultPayFactory(configuration);
    }

    @Bean(name = "nativePayService")
    @ConditionalOnProperty(value = "ltzf.sdk.config.enabled", havingValue = "true", matchIfMissing = false)
    public NativePayService nativePayService(PayFactory payFactory) {
        log.info("蓝兔支付 SDK 启动成功，扫码支付服务已装配");
        return payFactory.nativePayService();
    }

    @Bean(name = "h5PayService")
    @ConditionalOnProperty(value = "ltzf.sdk.config.enabled", havingValue = "true", matchIfMissing = false)
    public H5PayService h5PayService(PayFactory payFactory) {
        log.info("蓝兔支付 SDK 启动成功，H5支付服务已装配");
        return payFactory.h5PayService();
    }

}
