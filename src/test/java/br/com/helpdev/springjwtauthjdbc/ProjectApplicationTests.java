package br.com.helpdev.springjwtauthjdbc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ProjectApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Before
    public void setup() {
    }

    @Test
    public void pathSemSegurancaRetornoOK() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/open"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void pathComSegurancaRetornoFalha() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/test"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void realizaLoginComSucesso() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON).content(
                        "{\"username\":\"admin\",\"password\":\"helpdev\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void realizaLoginComSenhaErradaRetornoSemAutorizacao() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON).content(
                        "{\"username\":\"admin\",\"password\":\"xxxx\"}"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }


}
