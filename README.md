# 🚌 Ulaşım Rota Planlama Sistemi

Bu proje, kullanıcıların şehir içi toplu taşıma ihtiyaçlarını kişiselleştirilmiş kriterlerle en iyi şekilde karşılamak amacıyla geliştirilmiş bir rota planlama sistemidir.

## 📌 Proje Özeti

Ulaşım Rota Planlama Sistemi; kullanıcıların mevcut konumlarından hedef konumlarına en uygun toplu taşıma rotasını belirlemek üzere tasarlanmış bir web tabanlı uygulamadır. Sistem, kullanıcı profiline (öğrenci, yaşlı, genel), ödeme yöntemi tercihlerine ve diğer kişisel parametrelere göre çok kriterli optimizasyon algoritmaları çalıştırarak önerilerde bulunur.

## 🚀 Teknolojiler

### Backend
- Java
- Spring Boot
- RESTful API

### Frontend
- React.js
- Next.js
- Tailwind CSS
- Leaflet.js (harita için)

## 🧠 Kullanılan Yazılım Prensipleri

- Nesne Yönelimli Programlama (OOP)
- SOLID prensipleri
- Katmanlı mimari
- Tasarım desenleri:
  - Strategy Pattern
  - Factory Pattern
  - Singleton Pattern
  - Composite Pattern

## ⚙️ Temel Özellikler

- 🧑‍🎓 Kullanıcı profiline göre özelleştirilmiş rotalar
- 💸 Farklı ödeme yöntemleri (Nakit, Kredi Kartı, Kentkart)
- 🔀 Alternatif rota önerileri (ekonomik, hızlı, az transferli)
- 🗺️ Harita üzerinden interaktif rota görselleştirme
- 🧩 Entegre ulaşım modları: otobüs, tramvay, taksi, yürüme
- 📊 Çok kriterli değerlendirme (mesafe, süre, maliyet, konfor)

## 🧮 Algoritmalar

- **FindBestRoute:** Kullanıcı tipine göre en uygun rotayı seçer
- **OptimalPath:** Mesafe, süre ve maliyet arasında denge kurar
- **TransportTypePath:** Farklı ulaşım türleri arasında geçişi optimize eder
- **TaxiOnlyPath:** Taksi odaklı rotaları hesaplar
- **AlternativeRoutes:** Alternatif rota varyasyonları üretir
- **NearestStop:** En yakın durakları bulur
- **Haversine:** Coğrafi mesafe hesaplamaları için kullanılır

## 🧪 Örnek Kullanım Senaryosu

| Kullanıcı Tipi | Mesafe (km) | Süre (dk) | Ücret (₺) | Transfer Sayısı |
|----------------|-------------|-----------|-----------|------------------|
| Genel          | 8.2         | 35        | 32.50     | 1                |
| Öğrenci        | 8.5         | 39        | 27.75     | 2                |
| Yaşlı          | 7.8         | 32        | 0         | 1                |

Sistem, kullanıcı profiline göre indirimli, kısa ve konforlu rotalar sunar.

## 🔄 Genişletilebilirlik

Sistem açık/kapalı prensibine uygun olarak tasarlanmıştır. Yeni ulaşım türleri (örneğin elektrikli scooter) kolayca entegre edilebilir. Tek yapılması gereken:
- Yeni sınıf tanımlamak
- Gerekli arayüzleri uygulamak
- Mevcut yapıya minimum müdahaleyle sistemi genişletmek

## 🏗️ Kurulum

```bash
# Backend için
cd backend
./mvnw spring-boot:run

# Frontend için
cd frontend
npm install
npm run dev

