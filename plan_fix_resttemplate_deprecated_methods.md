# Plan: Fix Deprecated RestTemplate Methods

## Objetivo
Atualizar o arquivo RestTemplateConfig para substituir os métodos deprecados `setConnectTimeout()` e `setReadTimeout()` pelas alternativas recomendadas na versão 3.4.0+ do Spring Boot.

## Problema Identificado
- `setConnectTimeout(Duration)` está depreciado desde a versão 3.4.0
- `setReadTimeout(Duration)` está depreciado desde a versão 3.4.0
- Ambos estão marcados para remoção

## Arquivo Afetado
- `/home/rlevidev/Projetos/banking-pix-transaction-engine/pix-key-service/src/main/java/com/banking/pixkey/infrastructure/config/RestTemplateConfig.java`

## Passos Detalhados

### Passo 1: ✅ Pesquisar alternativas recomendadas (CONCLUÍDO)
- **Resultado**: Documentação oficial confirma as substituições:
  - `setConnectTimeout(Duration)` → `connectTimeout(Duration)`
  - `setReadTimeout(Duration)` → `readTimeout(Duration)`
- Fonte: Spring Boot 3.4.0 Deprecated List e OpenRewrite Documentation

### Passo 2: Atualizar o código
- Substituir `setConnectTimeout(Duration.ofSeconds(3))` pelo método recomendado
- Substituir `setReadTimeout(Duration.ofSeconds(5))` pelo método recomendado
- Manter os mesmos valores de timeout (3s para connect, 5s para read)

### Passo 3: Verificar imports
- Verificar se são necessários imports adicionais
- Remover imports não utilizados se houver

### Passo 4: ✅ Testar a compilação (CONCLUÍDO)
- **Resultado**: ✅ BUILD SUCCESS
- Projeto compilou sem erros ou warnings de depreciação
- Bean RestTemplate configurado corretamente

## Recursos Necessários
- Acesso ao código fonte
- Documentação do Spring Boot 3.4.0+
- Ambiente de compilação Java/Maven

## Critérios de Sucesso
- [x] Código compila sem warnings de depreciação
- [x] Timeout de conexão configurado para 3 segundos
- [x] Timeout de leitura configurado para 5 segundos
- [x] Bean RestTemplate funciona corretamente
- [x] Nenhum import desnecessário

## Possíveis Riscos
- **Risco Baixo**: Mudança de sintaxe pode quebrar a compilação temporariamente
- **Mitigação**: Verificar documentação oficial antes de fazer as alterações

## Alternativas Consideradas
- Manter os métodos depreciados (não recomendado devido à remoção futura)
- Usar configuração via application.properties (opção válida mas menos flexível)

## Decisão
Atualizar para os métodos recomendados mantendo a configuração programática para maior flexibilidade.
