package com.backendprojetointegrador.lajeDev.api;

import com.backendprojetointegrador.lajeDev.api.testsIntegration.CaracterisitcaTestIntegrationApi;
import com.backendprojetointegrador.lajeDev.api.testsIntegration.CategoriaTestIntegrationApi;
import com.backendprojetointegrador.lajeDev.api.testsIntegration.CidadeTestIntegrationApi;
import com.backendprojetointegrador.lajeDev.api.testsIntegration.ProdutoTestIntegrationApi;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@RunWith(Suite.class)
@SuiteClasses({
        CaracterisitcaTestIntegrationApi.class,
        CategoriaTestIntegrationApi.class,
        CidadeTestIntegrationApi.class,
        ProdutoTestIntegrationApi.class
})
public class SuiteTestsApi {
}
