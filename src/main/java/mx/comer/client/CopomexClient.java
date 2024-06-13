package mx.comer.client;

import java.util.List;
import java.util.ArrayList;
import org.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class CopomexClient {

    @Autowired
    private Environment propiedades;

    public List<String> direccion(){

        List<String> direccion = new ArrayList<>();

        try{

            HttpClient cliente = HttpClientBuilder.create().build();

            HttpGet operacion = new HttpGet(propiedades.getProperty("valor.direccion.copomex"));
            operacion.addHeader("Content-Type", "application/json");

            HttpResponse respuesta = cliente.execute(operacion);

            if(respuesta.getStatusLine().getStatusCode() == 200){

                String string = EntityUtils.toString(respuesta.getEntity());

                JSONObject salida = new JSONObject(string);

                if(salida.has("multiserviciosweb")){

                    JSONObject multiservicios = new JSONObject(salida.get("multiserviciosweb").toString());

                    if(multiservicios.has("response")){

                        JSONObject resultado = new JSONObject(multiservicios.get("response").toString());

                        if(resultado.has("cityName") && resultado.has("postalCode") && resultado.has("latitude") && resultado.has("longitude")){

                            direccion.add(resultado.get("cityName") + "");
                            direccion.add(resultado.get("postalCode") + "");
                            direccion.add(resultado.get("latitude") + "");
                            direccion.add(resultado.get("longitude") + "");

                            return direccion;

                        }else{

                            direccion.add("1");
                            return direccion;

                        }

                    }else{

                        direccion.add("1");
                        return direccion;

                    }

                }else{

                    direccion.add("1");
                    return direccion;

                }

            }else{

                direccion.add("1");
                return direccion;

            }

        }catch(Exception e){

            direccion.add("1");
            return direccion;

        }

    }

}
