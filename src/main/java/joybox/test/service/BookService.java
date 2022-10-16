package joybox.test.service;

import com.google.gson.Gson;
import joybox.test.exception.HttpException;
import joybox.test.model.BaseResponse;
import joybox.test.model.Book;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class BookService {

    public List<Book> getBookBySubject(String subject) throws HttpException {
        final String uri = "http://openlibrary.org/subjects/" + subject + ".json";

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Object> request = new HttpEntity<>(null, getHeaders());
            ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

            List<Object> worksResponse = (List<Object>) ((LinkedHashMap) response.getBody()).get("works");

            List<Book> resultList = new ArrayList<>();
            if (isNotEmptyOrNull(worksResponse)) {
                for (Object data : worksResponse) {
                    String bookKey = (String) ((LinkedHashMap) data).get("key");
                    String title = (String) ((LinkedHashMap) data).get("title");
                    int editionCount = (Integer) ((LinkedHashMap) data).get("edition_count");

                    List<Object> authorList = (List<Object>) ((LinkedHashMap) data).get("authors");
                    StringBuilder author = new StringBuilder();
                    if (isNotEmptyOrNull(authorList)) {
                        for (int i = 0; i < authorList.size(); i++) {
                            author.append((String) ((LinkedHashMap) authorList.get(i)).get("name"));

                            if (i == authorList.size() - 2) {
                                author.append(" & ");
                            } else if (i < authorList.size() - 2) {
                                author.append(", ");
                            }
                        }
                    }

                    resultList.add(new Book(title, author.toString(), editionCount));
                }
            }

            return resultList;
        } catch (HttpStatusCodeException e){
            Gson gson = new Gson();
            BaseResponse errorBody = gson.fromJson(e.getResponseBodyAsString(), BaseResponse.class);
            throw new HttpException(errorBody.getMessage(), e.getStatusCode());
        } catch (Exception e) {
            throw new HttpException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    public static boolean isNotEmptyOrNull(Object obj) {
        if (obj == null) return false;
        if (obj instanceof String)
            return !((String) obj).isEmpty();
        else if (obj instanceof Collection)
            return !((Collection<?>) obj).isEmpty();
        return true;
    }
}
