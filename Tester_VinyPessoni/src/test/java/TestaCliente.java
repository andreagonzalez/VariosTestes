import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;


public class TestaCliente {

    String enderecoApi = "http://localhost:8080";
    String recursoCliente = "/cliente";
    String recursoApagaTodos = "/apagaTodos";

    @Test
    @DisplayName("Quando pegar lista de clientes sem adicionar cliente, Então a lista deve estar vazia")
    public void pegaTodosClientes(){
        apagaTodosClientes();
        String respostaEsperada = "{}";

        given().
                contentType(JSON).
        when().
                get(enderecoApi).
        then().
                statusCode(200).
                assertThat().
                body(new IsEqual(respostaEsperada));

    }
    @Test
    @DisplayName("Quando cadastrar um cliente, Então o cliente é cadastrado com sucesso")
    public void cadastraCliente(){

        String corpoRequisicao = "{\n" +
                " \"nome\": \"Vinny\",\n" +
                " \"idade\": \"30\",\n" +
                " \"id\":\"1234\"\n" +
                "}";


        String respostaEsperada = "{\"1234\":" +
                "{\"nome\":\"Vinny\"," +
                "\"idade\":30," +
                "\"id\":1234,"+
                "\"risco\":0}"+
                "}";

        given().
                contentType(JSON).
                body(corpoRequisicao).
        when().
                post(enderecoApi+ recursoCliente).
        then().
                statusCode(201).
                assertThat().
                body(new IsEqual(respostaEsperada));

    }

    @Test
    @DisplayName("Quando atualizar um cliente, Então o cliente é atualizado com sucesso")
    public void atualizarCliente(){

        String corpoRequisicao = "{\n" +
                " \"nome\": \"Vinny\",\n" +
                " \"idade\": \"30\",\n" +
                " \"id\":\"1234\"\n" +
                "}";

        String corpoAtualizaoRequisicao = "{\n" +
                " \"nome\": \"Vinny Pessoni\",\n" +
                " \"idade\": \"31\",\n" +
                " \"id\":\"1234\"\n" +
                "}";


        String respostaEsperada = "{\"1234\":" +
                "{\"nome\":\"Vinny Pessoni\"," +
                "\"idade\":31," +
                "\"id\":1234,"+
                "\"risco\":0}"+
                "}";

        given().
                contentType(JSON).
                body(corpoRequisicao).
        when().
                post(enderecoApi+ recursoCliente);

        given().
                contentType(JSON).
                body(corpoAtualizaoRequisicao).
        when().
                put(enderecoApi+ recursoCliente).
        then().
                statusCode(200).
                assertThat().
                body(new IsEqual(respostaEsperada));
    }
    @Test
    @DisplayName("Quando deletar um cliente, Então o cliente é deletado com sucesso")
    public void deletarCliente(){
        String idParaDeletar = "/1234";

        String corpoRequisicao = "{\n" +
                " \"nome\": \"Vinny\",\n" +
                " \"idade\": \"30\",\n" +
                " \"id\":\"1234\"\n" +
                "}";

       String respostaEsperada = "CLIENTE REMOVIDO: { NOME: Vinny, IDADE: 30, ID: 1234 }";

        given().
                contentType(JSON).
                body(corpoRequisicao).
                when().
                post(enderecoApi+ recursoCliente);

        given().
                contentType(JSON).
        when().
                delete(enderecoApi+ recursoCliente +idParaDeletar).
                then().
                statusCode(200).
                assertThat().
                body(new IsEqual(respostaEsperada));
    }
    public void apagaTodosClientes(){
        String respostaEsperada = "{}";
        given().
                contentType(JSON).
                when().
                delete(enderecoApi+ recursoCliente +recursoApagaTodos).
                then().
                statusCode(200).
                assertThat().
                body(new IsEqual(respostaEsperada));

    }
}
