package com.br.monitoria.software.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.br.monitoria.software.dto.Student;
import com.br.monitoria.software.dto.Student2;
import com.br.monitoria.software.exception.StudentNotFoundException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

@Service
public class SheetsService {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/etc/secrets/monitoria-arquitetura-a3a2d34dadf0.json";
    private static final String SPREADSHEET_ID = "1g0JtAkmqGjd2mWeK9cMA34V2u1VNxMsrIzpCyxpV7-0"; 
    private static final String RANGE = "TabelaAlunosPontos!A:AG"; 
    private static final String RANGE_2 = "TabelaAlunosPontos2!A:AG";
    private static final Logger logger = Logger.getLogger(SheetsService.class.getName());

    // Column indices
    private static final int COL_MATRICULA = 0;
    private static final int COL_NOME = 1;
    private static final int COL_ATV_PERFIL = 2;
    private static final int COL_ATV1 = 3;
    private static final int COL_BAD1 = 4;
    private static final int COL_ATV2 = 5;
    private static final int COL_BAD2 = 6;
    private static final int COL_ATV3 = 7;
    private static final int COL_BAD3 = 8;
    private static final int COL_PERFIL = 9;
    private static final int COL_ATV4 = 10;
    private static final int COL_BAD4 = 11;
    private static final int COL_ATV5 = 12;
    private static final int COL_BAD5 = 13;
    private static final int COL_QUIZZ = 14;
    private static final int COL_LEARN = 15;
    private static final int COL_FORMS = 16;
    private static final int COL_OTIMOS_EXEMPLOS = 17;
    private static final int COL_BONS_RECURSOS = 18;
    private static final int COL_CUMPRIR_O_TEMPO = 19;
    private static final int COL_BASE_TEORICA = 20;
    private static final int COL_TRABALHO_EM_EQUIPE = 21;

    private static final int COL_MATRICULA2 = 0;
    private static final int COL_NOME2 = 1;
    private static final int COL_ENTREVISTA = 2;
    private static final int COL_VAL_ENTREVISTA = 3;
    private static final int COL_4 = 4;
    private static final int COL_VAL_4 = 5;
    private static final int COL_C4 = 6;
    private static final int COL_VAL_C4 = 7;
    private static final int COL_AVALIACAO_DE_ARQ = 8;
    private static final int COL_VAL_AVALIACAO_DE_ARQ = 9;
    private static final int COL_DOJO_4 = 10;
    private static final int COL_DOJO_C4 = 11;
    private static final int COL_MELHOR_ENTREVISTA = 13;
    private static final int COL_CRIATIVIDADE = 14;
    private static final int COL_RIGOR_ARQUIT = 15;
    private static final int COL_COMPLETUDE = 16;
    private static final int COL_CORRETUDE_TEC = 17;
 
    private HttpRequestInitializer getCredentials() throws IOException {
        // Verificar se o diretório /etc/secrets existe
        File secretsDir = new File("/etc/secrets");
        if (secretsDir.exists() && secretsDir.isDirectory()) {
            logger.info("Arquivos encontrados em /etc/secrets:");
            for (String fileName : secretsDir.list()) {
                logger.info(fileName);
            }
        } else {
            logger.severe("/etc/secrets não existe ou não é um diretório.");
        }

        // Tentativa de carregar credenciais da variável de ambiente
        InputStream in;
        String credentialsEnv = System.getenv("GOOGLE_CREDENTIALS_JSON");
        if (credentialsEnv != null && !credentialsEnv.isEmpty()) {
            logger.info("Carregando credenciais de variável de ambiente.");
            in = new ByteArrayInputStream(credentialsEnv.getBytes(StandardCharsets.UTF_8));
        } else {
            logger.info("Carregando credenciais de arquivo.");
            // Tentativa de carregar credenciais do arquivo local usando ClassPathResource
            ClassPathResource resource = new ClassPathResource(CREDENTIALS_FILE_PATH);
            in = resource.getInputStream();
        }

        // Criar as credenciais
        GoogleCredentials credentials = ServiceAccountCredentials.fromStream(in)
                .createScoped(SCOPES);

        // Retornar as credenciais adaptadas
        return new HttpCredentialsAdapter(credentials);
    }
     
    /*public HttpRequestInitializer getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
    // Recuperar as credenciais codificadas em Base64
    String encodedCredentials = System.getenv("GOOGLE_CREDENTIALS_BASE64");

    if (encodedCredentials == null || encodedCredentials.isEmpty()) {
        throw new IOException("Variável de ambiente GOOGLE_CREDENTIALS_BASE64 não definida.");
    }

    // Decodificar o conteúdo Base64 para obter o arquivo JSON
    byte[] decodedCredentials = Base64.getDecoder().decode(encodedCredentials);
    InputStream in = new ByteArrayInputStream(decodedCredentials);

    // Criar credenciais de conta de serviço
    GoogleCredentials credentials = GoogleCredentials.fromStream(in)
            .createScoped(SCOPES);

    // Retornar as credenciais como uma instância do HttpCredentialsAdapter
    return new HttpCredentialsAdapter(credentials);
    }*/
    

    public Student fetchStudentData(String studentId) throws IOException, GeneralSecurityException, StudentNotFoundException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
    
        ValueRange response = service.spreadsheets().values().get(SPREADSHEET_ID, RANGE).execute();
        List<List<Object>> values = response.getValues();
    
        if (values == null || values.isEmpty()) {
            throw new StudentNotFoundException("Estudante com essa matrícula " + studentId + " não está cadastrado na turma.");
        }
    
        for (List<Object> row : values) {
            if (row.get(COL_MATRICULA).toString().equals(studentId)) {
                Student student = new Student();
                student.setMatricula(row.get(COL_MATRICULA).toString());
                student.setNome(getValue(row, COL_NOME));
                student.setPerfis(getValue(row, COL_PERFIL));
                student.setPerfil(parseInteger(getValue(row, COL_ATV_PERFIL)));
                student.setAtv1(parseInteger(getValue(row, COL_ATV1)));
                student.setBad1(parseInteger(getValue(row, COL_BAD1)));
                student.setAtv2(parseInteger(getValue(row, COL_ATV2)));
                student.setBad2(parseInteger(getValue(row, COL_BAD2)));
                student.setAtv3(parseInteger(getValue(row, COL_ATV3)));
                student.setBad3(parseInteger(getValue(row, COL_BAD3)));
                student.setAtv4(parseInteger(getValue(row, COL_ATV4)));
                student.setBad4(parseInteger(getValue(row, COL_BAD4)));
                student.setAtv5(parseInteger(getValue(row, COL_ATV5)));
                student.setBad5(parseInteger(getValue(row, COL_BAD5)));
                student.setQuizz(parseInteger(getValue(row, COL_QUIZZ)));
                student.setLearn(parseInteger(getValue(row, COL_LEARN)));
                student.setForms(parseInteger(getValue(row, COL_FORMS)));
                student.setOtimosExemplos(parseInteger(getValue(row, COL_OTIMOS_EXEMPLOS)));
                student.setBonsRecursos(parseInteger(getValue(row, COL_BONS_RECURSOS)));
                student.setCumprirOTempo(parseInteger(getValue(row, COL_CUMPRIR_O_TEMPO)));
                student.setBaseTeorica(parseInteger(getValue(row, COL_BASE_TEORICA)));
                student.setTrabalhoEmEquipe(parseInteger(getValue(row, COL_TRABALHO_EM_EQUIPE)));
                return student;
            }
        }
        throw new StudentNotFoundException("Desculpe, não encontramos um estudante com a matrícula fornecida. Por favor, verifique a matrícula e tente novamente ou tente falar com o monitor.");
    }
    
    public Student2 fetchStudentData2(String studentId) throws IOException, GeneralSecurityException, StudentNotFoundException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
    
        ValueRange response = service.spreadsheets().values().get(SPREADSHEET_ID, RANGE_2).execute();
        List<List<Object>> values = response.getValues();
    
        if (values == null || values.isEmpty()) {
            throw new StudentNotFoundException("Estudante com essa matrícula " + studentId + " não está cadastrado na turma.");
        }
    
        for (List<Object> row : values) {
            if (row.get(COL_MATRICULA2).toString().equals(studentId)) {
                Student2 student = new Student2();
                student.setMatricula2(Integer.parseInt(row.get(COL_MATRICULA2).toString()));
                student.setNome2(getValue(row, COL_NOME2));
                student.setEntrevista(parseInteger(getValue(row, COL_ENTREVISTA)));
                student.setValEntrevista(parseInteger(getValue(row, COL_VAL_ENTREVISTA)));
                student.setCol4(parseInteger(getValue(row, COL_4)));
                student.setVal4(parseInteger(getValue(row, COL_VAL_4)));
                student.setC4(parseInteger(getValue(row, COL_C4)));
                student.setValC4(parseInteger(getValue(row, COL_VAL_C4)));
                student.setAvaliacaoDeArq(parseInteger(getValue(row, COL_AVALIACAO_DE_ARQ)));
                student.setValAvaliacaoDeArq(parseInteger(getValue(row, COL_VAL_AVALIACAO_DE_ARQ)));
                student.setDojo4(parseInteger(getValue(row, COL_DOJO_4)));
                student.setDojoC4(parseInteger(getValue(row, COL_DOJO_C4)));
                student.setMelhorEntrevista(parseInteger(getValue(row, COL_MELHOR_ENTREVISTA)));
                student.setCriatividade(parseInteger(getValue(row, COL_CRIATIVIDADE)));
                student.setRigorArquit(parseInteger(getValue(row, COL_RIGOR_ARQUIT)));
                student.setCompletude(parseInteger(getValue(row, COL_COMPLETUDE)));
                student.setCorretudeTec(parseInteger(getValue(row, COL_CORRETUDE_TEC)));
                return student;
            }
        }
        throw new StudentNotFoundException("Desculpe, não encontramos um estudante com a matrícula fornecida. Por favor, verifique a matrícula e tente novamente ou tente falar com o monitor.");
    }

    private String getValue(List<Object> row, int index) {
        if (index < row.size()) {
            logger.info(String.format("Fetching value from row: %s, index: %d", row, index));
            return row.get(index).toString();
        } else {
            logger.warning(String.format("Index %d out of bounds for row: %s", index, row));
            return "";
        }
    }

    private int parseInteger(String value) {
        if (value == null || value.isEmpty() || !isNumeric(value)) {
            logger.warning(String.format("Non-numeric value encountered: %s", value));
            return 0;
        }
        return Integer.parseInt(value);
    }

    private boolean isNumeric(String str) {
        try {
            return str != null && str.matches("\\d+");
        } catch (NumberFormatException e) {
            return false;
        }
    }
}