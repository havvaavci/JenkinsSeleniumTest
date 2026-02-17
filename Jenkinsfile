pipeline {
    agent any
    triggers {
        // Her gece saat 03:00'te otomatik çalıştır
        cron('0 3 * * *')
    }
    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Hangi tarayıcıda çalışsın?')
    }

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
                // Parametreyi -Dbrowser komutuyla Maven'a gönderiyoruz
                sh "mvn clean test -Dbrowser=${params.BROWSER}"
            }
        }

        stage('3. Adım: Raporları Yayınla') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('4. Adım: Dosyaları Arşivle') {
            steps {
                // Test sonuçlarını ve varsa screenshot klasörünü Jenkins üzerinde saklar
                archiveArtifacts artifacts: 'target/*.xml, target/*.png', allowEmptyArchive: true
            }
        }
    }

    post {
        post {
            always {
                allure includeProperties: false, results: [[path: 'target/allure-results']]
            }
        }
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