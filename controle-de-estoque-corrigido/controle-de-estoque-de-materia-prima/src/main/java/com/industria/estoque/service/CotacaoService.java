package com.industria.estoque.service;

import com.industria.estoque.dto.CotacaoExternaDTO;
import com.industria.estoque.dto.MaterialCotacaoDTO;
import com.industria.estoque.model.Material;
import com.industria.estoque.repository.MaterialRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class CotacaoService {

    private final RestClient restClient;
    private final MaterialRepository repository;

    public CotacaoService(
            RestClient restClient,
            MaterialRepository repository) {

        this.restClient = restClient;
        this.repository = repository;
    }

    public MaterialCotacaoDTO consultar(Long id) {

        // Busca o material no banco de dados.
        Material material = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Material não encontrado: " + id
                        ));

        try {

            // Consulta a cotação do dólar na API externa.
            CotacaoExternaDTO resposta = restClient.get()
                    .uri("/v1/latest?base=USD&symbols=BRL")
                    .retrieve()
                    .body(CotacaoExternaDTO.class);

            if (resposta == null || resposta.rates() == null) {
                throw new IllegalStateException(
                        "Resposta inválida da API de câmbio."
                );
            }

            Map<String, BigDecimal> taxas = resposta.rates();

            BigDecimal cotacao = taxas.get("BRL");

            if (cotacao == null || cotacao.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalStateException(
                        "Cotação do dólar indisponível."
                );
            }

            // Calcula o valor total do estoque em dólar.
            BigDecimal valorDolar =
                    material.getQuantidade()
                            .multiply(material.getPrecoUnitarioDolar());

            // Converte o valor para reais.
            BigDecimal valorReais = valorDolar
                    .multiply(cotacao)
                    .setScale(2, RoundingMode.HALF_UP);

            // Retorna dados próprios + dados externos.
            return new MaterialCotacaoDTO(
                    material.getId(),
                    material.getNome(),
                    material.getQuantidade(),
                    material.getPrecoUnitarioDolar(),
                    valorDolar,
                    cotacao,
                    valorReais,
                    resposta.date(),
                    true,
                    "Frankfurter API"
            );

        } catch (RestClientException | IllegalStateException e) {

            // Fallback: mantém a aplicação funcionando
            // mesmo quando a API externa estiver indisponível.

            return new MaterialCotacaoDTO(
                    material.getId(),
                    material.getNome(),
                    material.getQuantidade(),
                    material.getPrecoUnitarioDolar(),
                    material.getQuantidade()
                            .multiply(material.getPrecoUnitarioDolar()),
                    null,
                    null,
                    null,
                    false,
                    "Cotação externa indisponível"
            );
        }
    }
}