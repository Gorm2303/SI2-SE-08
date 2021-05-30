# SI2-SE-08 project
Dette Java-projekt er udgivet af fem software ingeniørstuderende på Syddansk Universitet, hvor gruppen har fået stillet en opgave af TV2 og SDU. Hensigten med projektet er at finde en softwareløsning til TV2's krediteringsproblem. Projektets målgruppe er TV2 og deres seere. TV2 kan anvende systemet for at kreditere danske produktioner, og deres seere kan anvende det for at se disse krediteringer. 

Tak til projektvejleder Henrik Larsen for hans støtte gennem projektet.

## Framework, software og bibliotek versioner
Følgende versioner af software er brugt til udviklingen af dette program:  
SDK: JDK 15  
JavaFX: openjfx 15  
PostgreSQL: 13.1  

## Opsætning
For at bruge programmet skal der først laves en PostreSQL database med navnet "CreditsSystem". Denne database skal instantieres med SQL-scriptet som findes i filen "2SE08SQL.sql" som ligger i dette projekts "resources" fil. SQL-scriptet laver alle de nødvendige tabeller, og fylder dem op med data.

Åben projektet i IntelliJ, det er muligvis nødvendigt at fortælle IntelliJ at man stoler på projektet, pga. Maven. Åben "Run configurations" for "main" og sæt VM options til at være "*--module-path [path]\textbackslash lib --add-modules javafx.controls,javafx.fxml*", hvor "*[path]\textbackslash lib*" er filstien til JavaFX biblioteket på brugerens computer. Derefter skal der ændres på "CLI arguments" som skal sættes til "*databaseIP databasePORT databaseNAME databaseUSERNAME databasePASSWORD*". Disse skal selvfølgelig tilpasses til at have de rigtige værdier som passer til brugerens database, som f.eks. "*localhost 5432 CreditsSystem postgres password*", i tilfælde af at databasen er hostet lokalt, brugernavnet er "postgres" og kodeordet er "password".

## Brug af programmet
Når projektet er opsat som beskrevet, kan programmet blive kørt (kør "main"). Det første brugeren bliver mødt af er en skærm med to muligheder: Se krediteringer, eller log ind. Log ind er ikke implementeret, så derfor skal der trykkes på "Se krediteringer". Efter dette trin er GUI'en meget intuitiv, og det foreslås at forsøge at forsøge at bruge programmet uden at læse videre til at starte med. Hvis programmet er for forvirrende, kan der læses videre her.

"Se krediteringer" knappen tager brugeren til hovedmenuen, hvor der kan browses igennem krediteringer af produktioner, medvirkende og organisationer. I den øverste bjælke kan man vælge hvilken type man vil browse igennem, og der kan også søges efter specifikke personer, organisationer eller produktioner.

Til venstre ser man en liste af den type krediteringer som man har valgt, og hvis man klikker på én af disse, vil en beskrivelse af denne dukke op i det store tekstfelt i midten af vinduet.

I højre bjælke er der to knapper, den ene - tilføje produktion - er altid mulig at bruge, mens den anden - rediger produktion - kun kan trykkes på hvis der er valgt en produktion. Begge disse knapper tager brugeren til det samme vindue, men hvis man vælger at redigerer vil felterne allerede være udfyldt, mens hvis man vælger at tilføje, vil felterne være tomme.

I dette nye vindue, hvor man kan tilføje eller fjerne en produktion, er der en del forskellige felter. "Navn" feltet er feltet hvor man skriver produktionens navn. "Dato" er feltet hvor man vælger datoen produktionen er lavet i - man kan også skrive datoen i formatet "19.02.2015"(19. februar 2015). Herefter er feltet med længden af produktionen i minutter, og herunder tilføjes en organisation som producerede produktionen. Dette gøres ved at søge efter organisationens navn (indtast søgeord og tryk på søgeknappen ved siden af tekstfeltet), og dette vil give den lille "drop down choicebox" med organisationer som passede på søgningen. Klik på den rigtige organisation, og gå så videre til krediteringen af medvirkende organisationer. De næste trin kan kun udføres når de første fire felter er udfyldt. 

En medvirkende organisation tilføjes ved at klikke på "Tilføj endnu en organisation" som er i bjælken til højre. Dette åbner endnu en "drop down choice box" som fungerer på samme måde som før, men søgefeltet til denne er i højre sidebjælke. Der kan også fjernes en medvirkende organisation i tilfælde af fejl eller redigering.

En kreditering af en person sker ved at tilføje rollen som den medvirkende fungerede som: klik "Tilføj endnu en rolle". Rollen skrives i "Rolle" tekstfeltet, og den medvirkende findes ligesom man finder en medvirkende organisation: søgefeltet "Søg efter medvirkende" i den højre sidebjælke. Man kan også tilføje endnu en medvirkende til en rolle med "Tilføj endnu en medvirkende" knappen som er ved siden af den tilhørende rolle.

Hvis en organisation eller medvirkende ikke findes i systemet, kan med "Opret ny medvirkende" og "Opret ny organisation" tilføje disse til systemet. Disse knapper bringer et nyt lille vindue op, med et navnefelt og for en medvirkende dukker et fødselsdato felt også op.

Når man er tilfreds med produktionen kan man gemme den med "Gem produktion" knappen, men man kan også annullere ændringerne med "Annuller ændringer" knappen. Hvis man redigerer en produktion kan man også slette denne med "Slet produktion" knappen.
