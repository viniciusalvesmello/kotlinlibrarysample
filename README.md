# Kotlin Library Sample

Esse projeto foi criado com o intúito de servir de amostra para o artigo [Criando, configurando e publicando uma biblioteca Kotlin](https://medium.com/fretebras-tech/criando-configurando-e-publicando-uma-biblioteca-kotlin-3ed3e5779f3b).

## Como importar a biblioteca?

Adicione o repotitory no projeto.

```kotlin
        maven {
            url "https://viniciusalvesmello.jfrog.io/artifactory/kotlin-library"
            credentials {
                username "ReadEmail@email.com"
                password "Email#123456"
            }
        }
```

Inclua a biblioteca nas dependêndias do projeto.

```kotlin
dependencies {
    implementation 'io.github.viniciusalvesmello:kotlinlibrarysample:1.1.0'
}
```