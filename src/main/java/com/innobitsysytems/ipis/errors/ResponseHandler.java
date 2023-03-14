/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Response Handler
 */
package com.innobitsysytems.ipis.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author AMIT
 */
public class ResponseHandler {

    private static LocalDateTime timestamp = LocalDateTime.now();

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
      
        switch (status.value()) {

            case 200: {
            	
            	
                map.put("status", status.value());
                map.put("message", message);
                if (responseObj != null) {
                    map.put("data", responseObj);
                }
                map.put("timestamp", timestamp);
                break;

            }
            case 201: {
                map.put("status", status.value());
                map.put("message", message);
                if (responseObj != null) {
                    map.put("data", responseObj);
                }
                map.put("timestamp", timestamp);
                break;

            }
            case 400: {

                map.put("status", status.value());
                map.put("errorMsg", message);
                map.put("timestamp", timestamp);

                break;

            }
            case 402: {

                map.put("status", status.value());
                map.put("errorMsg", message);
                map.put("timestamp", timestamp);

                break;

            }
            case 403: {

                map.put("status", status.value());
                map.put("errorMsg", message);
                map.put("timestamp", timestamp);

                break;

            }
            case 404: {

                map.put("status", status.value());
                map.put("errorMsg", message);
                map.put("timestamp", timestamp);

                break;

            }
            case 405: {

                map.put("status", status.value());
                map.put("errorMsg", message);
                map.put("timestamp", timestamp);

                break;

            }
            case 409: {

                map.put("status", status.value());
                map.put("errorMsg", message);
                map.put("timestamp", timestamp);
                break;

            }
            case 500: {

                map.put("status", status.value());
                map.put("errorMsg", message);
                map.put("timestamp", timestamp);
                break;

            }

            default:
                map.put("status", status.value());
                map.put("message", message);
                map.put("timestamp", timestamp);
        }
        return new ResponseEntity<Object>(map, status);
    }

}
