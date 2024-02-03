package backend.zip.service.map;

import backend.zip.dto.brokeritem.response.BrokerItemAddressResponse;
import backend.zip.dto.useritem.response.UserItemAddressResponse;
import backend.zip.global.exception.map.AddressException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.broker.BrokerItemRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {
    @Value("${kakao.api-key}")
    private String apiKey;

    public String getKaKaoApiFromInputAddress(String roadFullAddress) {
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        String jsonString = null;

        try {
            // URL-Encoding
            roadFullAddress = URLEncoder.encode(roadFullAddress, "UTF-8");

            // 요청 URL 생성
            String address = apiUrl + "?query=" + roadFullAddress;

            // URL 객체 생성
            URL url = new URL(address);

            // URL Connection 생성
            URLConnection urlConnection = url.openConnection();

            // Header 값 설정
            urlConnection.setRequestProperty("Authorization", "KakaoAK " + apiKey);

            // StringBuffer에 값을 넣고 String 형태로 변환 후 jsonString을 return 함
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            StringBuffer docJson = new StringBuffer();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                docJson.append(line);
            }

            jsonString = docJson.toString();
            System.out.println("jsonString = " + jsonString);
            bufferedReader.close();

            // JSON 응답 파싱하여 주소 검색 결과 확인
            JSONObject jsonResponse = new JSONObject(jsonString);
            JSONArray documents = jsonResponse.getJSONArray("documents");

            if (documents.length() == 0) {
                throw new AddressException(ErrorStatus.ADDRESS_NOT_FOUND);
            }
//        } catch (UnsupportedEncodingException e) {
//            throw new AddressException(ErrorStatus.ADDRESS_IO_EXCEPTION);
//        } catch (MalformedURLException e) {
//            throw new AddressException(ErrorStatus.ADDRESS_IO_EXCEPTION);
        } catch (IOException e) {
            throw new AddressException(ErrorStatus.ADDRESS_NOT_FOUND);
        }
        return jsonString;
    }

    @Override
    public HashMap<String, String> getXYMapFromJson(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> XYMap = new HashMap<String, String>();

        try {
            TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){};
            Map<String, Object> jsonMap = mapper.readValue(jsonString, typeRef);

            @SuppressWarnings("unchecked")
            List<Map<String, String>> docList = (List<Map<String, String>>) jsonMap.get("documents");

            Map<String, String> adList = (Map<String, String>) docList.get(0);
            XYMap.put("x",adList.get("x"));
            XYMap.put("y", adList.get("y"));

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return XYMap;
    }

    @Override
    public BrokerItemAddressResponse returnAddressAndDongAndXY(String jsonString) {
        JSONObject jsonResponse = new JSONObject(jsonString);
        JSONArray documents = jsonResponse.getJSONArray("documents");
        JSONObject document = documents.getJSONObject(0); // 첫 번째 문서 객체를 가져옴

        String address = document.getJSONObject("address").getString("address_name");
        String dong = document.getJSONObject("address").getString("region_3depth_name");
        Double x = Double.valueOf(document.getString("x"));
        Double y = Double.valueOf(document.getString("y"));

        return new BrokerItemAddressResponse(address, dong, x, y);
    }


    // 아예 함수 재사용할수도있는데 혼란없이 구분하기 좋게 일단은 따로 만들었습니다.
    @Override
    public UserItemAddressResponse returnUserItemAddressAndDong(String jsonString) {
        JSONObject jsonResponse = new JSONObject(jsonString);
        JSONArray documents = jsonResponse.getJSONArray("documents");
        JSONObject document = documents.getJSONObject(0); // 첫 번째 문서 객체를 가져옴

        String address = document.getJSONObject("address").getString("address_name");
        String dong = document.getJSONObject("address").getString("region_3depth_name");

        return new UserItemAddressResponse(address, dong);
    }
}
