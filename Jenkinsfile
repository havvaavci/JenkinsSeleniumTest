pipeline {
    agent any

    // Jenkins'e hangi Maven sürümünü kullanacağını burada söylüyoruz
    tools {
        maven 'Maven3' // Jenkins -> Manage Jenkins -> Tools kısmındaki isimle aynı olmalı
    }

    triggers {
        cron('0 3 * * *') // Gece 3'te otomatik çalışma
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Tarayıcı seçiniz:')
    }

    stages {
        stage('1. Kodları Güncelle') {
            steps {
                checkout scm
            }
        }

        stage('2. Testleri Koştur') {
            steps {
                // 'mvn' komutu artık 'tools' sayesinde tanınacak
                sh "mvn clean test -Dbrowser=${params.BROWSER}"
            }
        }
    }

    post {
        always {
            // Standart JUnit raporu
            junit '**/target/surefire-reports/*.xml'

            // Ekran görüntüleri (target içinde .png varsa)
            archiveArtifacts artifacts: 'target/*.png', allowEmptyArchive: true

            // Allure raporu (pom.xml'deki verileri okur)
            allure includeProperties: false, results: [[path: 'target/allure-results']]
        }

        failure {
            echo '❌ Test başarısız! Logları ve ekran görüntülerini kontrol edin.'
        }

        success {
            echo '✅ Tebrikler! Test başarıyla bitti.'
        }
    }
}