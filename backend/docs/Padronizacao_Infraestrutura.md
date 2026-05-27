# 🎛️ Padronização de Infraestrutura: Gerenciamento do Maven Wrapper Global

Em uma arquitetura Monorepo Multi-módulos, a consistência do build é um fator crítico. Se desenvolvedores diferentes usarem versões distintas do 
Maven instaladas localmente em suas máquinas, o projeto pode apresentar comportamentos bizarros e falhas de compilação difíceis de rastrear.

Para resolver isso, utilizamos o **Maven Wrapper**. Ele garante que o projeto baixe e utilize exatamente a mesma versão do Maven em qualquer 
ambiente (inclusive em esteiras de automação de CI/CD como o GitHub Actions).

Abaixo está o procedimento sênior para **centralizar** o Maven Wrapper na raiz global e expurgar os arquivos duplicados que costumam ser gerados 
incorretamente dentro dos subprojetos filhos.

---

## 🧭 Passo 1: Inicializando o Wrapper Centralizado na Raiz

Abra o seu terminal de comandos, navegue até a **pasta raiz global do projeto** (`backend/`) e execute o seguinte comando:

```bash
mvn wrapper:wrapper
```

### 🧠 Por que isso é importante?

Ao rodar este comando no topo da estrutura, o Maven analisa o seu `pom.xml` pai e cria três arquivos essenciais na raiz:

1. `mvnw.cmd`: Script de execução dedicado a ambientes Windows.
2. `mvnw`: Script de execução dedicado a ambientes Linux/Mac.
3. `.mvn/wrapper/maven-wrapper.properties`: Arquivo que define qual versão exata do Maven o ecossistema deve adotar.
4. **A importância da centralização:** Com isso, o projeto pai passa a ser o único "maestro" do build. Em vez de gerenciar builds isolados 
dentro de cada pasta de módulo, você comandará o ciclo de vida de todo o ecossistema a partir de um único ponto.

---

## 🧹 Passo 2: Expurgando os Wrappers Duplicados dos Módulos

É muito comum que geradores automáticos de projetos (como o Spring Initializr) criem arquivos de wrapper isolados dentro das pastas dos módulos 
filhos (`AgendaContatoConsumer` e `AgendaContatoProducer`). Em um Monorepo profissional, isso é considerado um antipadrão de arquitetura que polui 
o histórico de commits do Git e gera conflitos no pipeline de deploy.

Para remover com segurança esses arquivos duplicados e desnecessários, execute os comandos abaixo de acordo com o sistema operacional da sua máquina:

### No Windows (Via PowerShell, executado na raiz `backend/`):

```shell
# Remove o lixo estrutural do módulo Consumer
Remove-Item -Path "AgendaContatoConsumer/mvnw", "AgendaContatoConsumer/mvnw.cmd" -Force
Remove-Item -Path "AgendaContatoConsumer/.mvn" -Recurse -Force

# Remove o lixo estrutural do módulo Producer
Remove-Item -Path "AgendaContatoProducer/mvnw", "AgendaContatoProducer/mvnw.cmd" -Force
Remove-Item -Path "AgendaContatoProducer/.mvn" -Recurse -Force
```

### No Linux ou Mac (Via Terminal Bash, executado na raiz `backend/`):

```bash
# Remove o lixo estrutural do módulo Consumer
rm -rf AgendaContatoConsumer/mvnw AgendaContatoConsumer/mvnw.cmd AgendaContatoConsumer/.mvn

# Remove o lixo estrutural do módulo Producer
rm -rf AgendaContatoProducer/mvnw AgendaContatoProducer/mvnw.cmd AgendaContatoProducer/.mvn
```

### 🧠 Por que isso é importante?

Manter esses arquivos espalhados faz com que o Git rastreie códigos repetidos sem necessidade. Além disso, se o pipeline de CI/CD tentar buildar 
o projeto executando os scripts internos de um filho, a compilação vai quebrar porque os módulos dependem do projeto Pai e do irmão `Shared` para 
existir. A remoção força o uso correto do fluxo centralizado.

---

## ⚡ Passo 3: Limpando o Cache de Rastreamento do Git

Se você já tinha adicionado os arquivos ou as pastas da sua IDE (como a pasta `.idea/` do IntelliJ) no controle do Git antes de configurar o arquivo 
`.gitignore`, o Git continuará monitorando as alterações deles.

Para forçar o Git a aplicar as novas regras do .gitignore e esquecer os arquivos deletados e mascarados, execute os dois comandos abaixo na raiz 
do repositório:

```bash
# 1. Remove recursivamente todos os arquivos do cache de rastreamento do Git
git rm -r --cached .

# 2. Readiciona os arquivos respeitando as novas regras restritivas do .gitignore
git add .
```

### 🧠 Por que isso é importante?

O Git funciona como um "cão de guarda": uma vez que ele começou a vigiar um arquivo, ele não para de olhar mesmo se o arquivo for adicionado 
ao `.gitignore` depois. O comando `git rm -r --cached .` "limpa a memória" do Git. Quando você roda o `git add .` logo em seguida, o Git reavalia 
a estrutura inteira do zero, ignorando de verdade a pasta `.idea/`, os arquivos de cache locais e os wrappers antigos que apagamos no Passo 2.

---

## 📋 Resultado Esperado no Commit

Após concluir a limpeza, ao rodar o comando `git status`, a sua tela de preparação para commit deve estar limpa e conter apenas a estrutura global 
estável.

- **Na Raiz Global (`backend/`):** Deve constar a criação/alteração dos scripts `mvnw`, `mvnw.cmd` e do diretório `.mvn/wrapper/`.
- **Nas pastas dos subprojetos filhos:** Não deve haver nenhuma menção a arquivos de wrapper.
- **Nas pastas da IDE (`\.idea`):** Devem sumir completamente da listagem de modificações, protegendo suas preferências de ambiente locais.

A partir de agora, para buildar e testar o projeto inteiro de forma sênior e integrada, basta disparar o comando na raiz:

### No Windows

```bash
./mvnw clean install
```

### No Linux/Mac

```bash
./mvnw clean install
```

#### Observação

Caso dê erro no início, tente executar este comando. Ele builda o projeto inteiro sem realizar testes:

```bash
./mvnw clean install -DskipTests
```
