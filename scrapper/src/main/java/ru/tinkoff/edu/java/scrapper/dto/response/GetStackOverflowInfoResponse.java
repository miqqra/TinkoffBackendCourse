package ru.tinkoff.edu.java.scrapper.dto.response;

public record GetStackOverflowInfoResponse(StackOverflowItems[] items){
    public record StackOverflowItems(long last_activity_date){}
}

//public class GetStackOverflowInfoResponse {
//
//    OffsetDateTime lastActivityDate;
//
//    @JsonProperty("items")
//    private void unpackNameFromNestedObject(List<Map<String, String>> items) {
//        lastActivityDate = OffsetDateTime.parse(items.get(0).get("last_activity_date"));
//    }
//}

