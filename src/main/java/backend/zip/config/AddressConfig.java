package backend.zip.config;

import backend.zip.dto.brokeritem.response.AddressResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class AddressConfig {
    public static List<AddressResponse> extractAddress(String kaKaoApiFromInputAddress) {
        JSONObject inputJson = new JSONObject(kaKaoApiFromInputAddress);
        JSONArray documents = inputJson.getJSONArray("documents");
        List<AddressResponse> responseList = new ArrayList<>();

        for (int i = 0; i < documents.length(); i++) {
            JSONObject document = documents.getJSONObject(i);
//            JSONObject address = document.getJSONObject("address").getJSONObject("address_name");
//            JSONObject addressName = document.getJSONObject("address_name");

            AddressResponse response = AddressResponse.of(document);
            responseList.add(response);
        }
        return responseList;
    }
}
