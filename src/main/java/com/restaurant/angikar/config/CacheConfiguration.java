package com.restaurant.angikar.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.restaurant.angikar.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.restaurant.angikar.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.restaurant.angikar.domain.User.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Authority.class.getName());
            createCache(cm, com.restaurant.angikar.domain.User.class.getName() + ".authorities");
            createCache(cm, com.restaurant.angikar.domain.PincodeMaster.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Location.class.getName());
            createCache(cm, com.restaurant.angikar.domain.ApplicationUser.class.getName());
            createCache(cm, com.restaurant.angikar.domain.ApplicationUser.class.getName() + ".addresses");
            createCache(cm, com.restaurant.angikar.domain.ApplicationUser.class.getName() + ".otps");
            createCache(cm, com.restaurant.angikar.domain.ApplicationUser.class.getName() + ".mealPlans");
            createCache(cm, com.restaurant.angikar.domain.ApplicationUser.class.getName() + ".carts");
            createCache(cm, com.restaurant.angikar.domain.Otp.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Restaurant.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Restaurant.class.getName() + ".admins");
            createCache(cm, com.restaurant.angikar.domain.Restaurant.class.getName() + ".itemPincodeMappings");
            createCache(cm, com.restaurant.angikar.domain.Banner.class.getName());
            createCache(cm, com.restaurant.angikar.domain.FoodCategory.class.getName());
            createCache(cm, com.restaurant.angikar.domain.FoodCategory.class.getName() + ".items");
            createCache(cm, com.restaurant.angikar.domain.Price.class.getName());
            createCache(cm, com.restaurant.angikar.domain.QtyUnit.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Quantity.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Nutrition.class.getName());
            createCache(cm, com.restaurant.angikar.domain.IngredienceMaster.class.getName());
            createCache(cm, com.restaurant.angikar.domain.IngredienceMaster.class.getName() + ".items");
            createCache(cm, com.restaurant.angikar.domain.Item.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Item.class.getName() + ".itemPincodeMappings");
            createCache(cm, com.restaurant.angikar.domain.Item.class.getName() + ".ingrediences");
            createCache(cm, com.restaurant.angikar.domain.Item.class.getName() + ".mealPlanItems");
            createCache(cm, com.restaurant.angikar.domain.WeightInfo.class.getName());
            createCache(cm, com.restaurant.angikar.domain.CalorieInfo.class.getName());
            createCache(cm, com.restaurant.angikar.domain.MealPlanSettings.class.getName());
            createCache(cm, com.restaurant.angikar.domain.MealPlanSettings.class.getName() + ".plans");
            createCache(cm, com.restaurant.angikar.domain.MealPlanSettings.class.getName() + ".avoidLists");
            createCache(cm, com.restaurant.angikar.domain.Avoid.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Avoid.class.getName() + ".mealPlanSettings");
            createCache(cm, com.restaurant.angikar.domain.MealPlan.class.getName());
            createCache(cm, com.restaurant.angikar.domain.MealPlan.class.getName() + ".meals");
            createCache(cm, com.restaurant.angikar.domain.MealPlanItem.class.getName());
            createCache(cm, com.restaurant.angikar.domain.MealPlanItem.class.getName() + ".skipDates");
            createCache(cm, com.restaurant.angikar.domain.MealPlanItem.class.getName() + ".items");
            createCache(cm, com.restaurant.angikar.domain.SkipDate.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Discount.class.getName());
            createCache(cm, com.restaurant.angikar.domain.CartItem.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Cart.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Cart.class.getName() + ".items");
            createCache(cm, com.restaurant.angikar.domain.OrderItem.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Coupon.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Order.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Order.class.getName() + ".items");
            createCache(cm, com.restaurant.angikar.domain.Order.class.getName() + ".payments");
            createCache(cm, com.restaurant.angikar.domain.Payment.class.getName());
            createCache(cm, com.restaurant.angikar.domain.Payment.class.getName() + ".refunds");
            createCache(cm, com.restaurant.angikar.domain.Refund.class.getName());
            createCache(cm, com.restaurant.angikar.domain.ItemPincodeMapping.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
