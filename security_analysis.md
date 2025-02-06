# SECURITY ANALYSIS - Timber & Stone Airbnb

## 1. Projektöversikt

- Beskriv kort vad er applikation gör
  En AirBnb sida för användare att lägga ut annonser och hyra ut/hyra bostäder.

- Lista huvudfunktionaliteter
  CRUD - user, rentals, reviews, bookings
  Boka boenden efter datum
  Genomföra "betalning"
  Meddela andra användare
  Se historik
  Söka och filtrera annonser
  Anonymisera användare för att behålla historik.
  Skapa/visa tillgänglighet för boenden
  Kategorier för annonstyper
  Bekvämligheter

- Identifiera vilka användare/roller som finns i systemet
  Utloggad
  Bas-user
  Host-user
  Admin

## 2. Känslig Data

### 2.1 Identifiera känslig information
Kryssa i det som stämmer för er, fyll på med fler om det behövs.

- [x] Personuppgifter
- [x] Inloggningsuppgifter
- [ ] Betalningsinformation
- [x] Annan känslig affärsdata

### 2.2 Dataskyddsåtgärder

Beskriv hur du skyddar den känsliga informationen:

- Kryptering (vilken data krypteras och hur?)
  Lösenord med hash och salt
  JWT kryptering (JSON Web Token)

- Säker datalagring
  Authentication, role-based-authorization

- Säker dataöverföring
  JWT, Cookies

## 3. Autentisering & Auktorisering

### 3.1 Inloggningssäkerhet

Kryssa i det som finns med/det ni har hanterar eller ska hantera i er applikation

- [x] Lösenordskrav (längd, komplexitet)
- [x] Hantering av misslyckade inloggningsförsök
- [ ] Session hantering
- [x] JWT/Token säkerhet

### 3.2 Behörighetskontroll

Kryssa i det som finns med/det ni har hanterat eller ska hantera i er applikation

- [x] Olika användarnivåer/roller
- [x] Åtkomstkontroll för endpoints
- [x] Validering av användarrättigheter

## 4. API Säkerhet

### 4.1 Input Validering

Kryssa i det som finns med/det ni har hanterar eller ska hantera i er applikation. 
Kryssa i även om vissa är disabled men skriv inom parentes disabled

- [x] Validering av alla användarinput
- [x] Skydd mot NoSQL Injection
- [x] Skydd mot XSS
- [x] Skydd mot CSRF (disabled i dev)

### 4.2 API Endpoints

Kryssa i det som finns med/det ni har hanterat eller ska hantera i er applikation

- [x] HTTPS användning (disabled i dev)
- [ ] Rate limiting
- [x] CORS konfiguration (disabled i dev)
- [x] Error handling (inga känsliga felmeddelanden) (disabled i dev)

## 5. Implementerade Säkerhetsåtgärder

Plocka ut det delar ur Spring Security som ni tycker är viktigast, räcker med ett par kod snuttar.
Lista konkreta säkerhetsimplementeringar:

```java
// Exempel på säkerhetskonfiguration
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Din säkerhetskonfiguration här
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

     public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
        http
                // CORS config
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // CSRF, disable in dev
                // OBS should not be disabled in production
                .csrf(csrf -> csrf.disable())
                // define url-based rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/host/**").hasRole("HOST")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "HOST", "ADMIN")
                        .requestMatchers("/auth/**").permitAll()
                        // user needs to be logged for any other requests
                        .anyRequest().authenticated()
                )

        // disable session due to jwt statelessness
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // add jwt filter before standard filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
```

## 6. Kvarstående Risker

OBS! Kan fyllas i mot slutet av projektet

- Lista kända säkerhetsrisker som behöver åtgärdas
- Förslag på framtida förbättringar

## Tips för genomförande

1. Börja med att identifiera känslig data i systemet
2. Fokusera på de viktigaste säkerhetsaspekterna först
3. Dokumentera säkerhetsval och motivera dem

## Vanliga Spring Security-komponenter att överväga

- SecurityFilterChain
- UserDetailsService
- PasswordEncoder
- JwtAuthenticationFilter
