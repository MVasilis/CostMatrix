package com.cardcost.costapplication.thirdparty.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BinListClientResponse {
    public Number number;
    public String scheme;
    public String type;
    public String brand;
    public boolean prepaid;
    public Country country;
    public Bank bank;

    @Data
    public static class Bank{
        public String name;
        public String url;
        public String phone;
        public String city;
    }

    @Data
    public static class Country{
        public String numeric;
        public String alpha2;
        public String name;
        public String emoji;
        public String currency;
        public int latitude;
        public int longitude;
    }

    @Data
    public static class Number{
        public int length;
        public boolean luhn;
    }
}

