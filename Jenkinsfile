pipeline {
    agent any

    tools {
        // Manage Jenkins > Tools kısmında Maven'a verdiğin ismi buraya yaz (Genelde 'Maven3' olur)
        maven 'Maven3'
    }

    stages {
        stage('1. Adım: Kodları Çek') {
            steps {
                checkout scm
            }
        }

        stage('2. Adım: Testleri Koştur') {
            steps {
                // Windows ise 'bat', Linux/AWS ise 'sh' kullanılır
                sh 'mvn clean test'
            }
        }

        stage('3. Adım: Raporları Yayınla') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }
    }

    post {
        failure {
            mail to: 'havvabuyukyalcin@gmail.com',
                    subject: "HATA: ${currentBuild.fullDisplayName} Başarısız!",
                    body: "Testler sırasında hata oluştu. Detaylar için buraya bak: ${env.BUILD_URL}"
        }
        success {
            echo 'Tebrikler! Tüm testler başarıyla tamamlandı.'
        }
    }
}