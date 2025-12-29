package com.TuBes.HewanKu;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cors_sequence")
    @SequenceGenerator(name="cors_sequence", sequenceName="cors_sequence", allocationSize=100)
    private Long id;
    
}