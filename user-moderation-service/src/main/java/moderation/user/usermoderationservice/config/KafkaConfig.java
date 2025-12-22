package moderation.user.usermoderationservice.config;

//@Configuration
//public class KafkaConfig {
//
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    @Bean
//    public ConsumerFactory<String, UserCreatedEvent> userEventConsumerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "book-author-group");
//        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.library.dto");
//        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, UserCreatedEvent.class.getName());
//
//        JsonDeserializer<UserCreatedEvent> jsonDeserializer = new JsonDeserializer<>(UserCreatedEvent.class);
//        jsonDeserializer.setRemoveTypeHeaders(false);
//
//        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), jsonDeserializer);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent>
//    userEventKafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(userEventConsumerFactory());
//        return factory;
//    }
//}
