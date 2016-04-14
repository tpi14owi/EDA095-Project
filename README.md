Setup
=====

Nöjet i att använda eclipse tillsammans med git
-----------------------------------------------

###  Konsten att importera ett projekt utan att gråta

1. Högerklicka i Package Explorer
2. Import..
3. Git
4. Projects from Git
5. Clone URI
6. fyll antingen i:
  * git@github.com:dat12fda/EDA095-Project.git, protocol är ssh 
  * ELLER
  * https://github.com/dat12fda/EDA095-Project.git, protocol är https 
  * beroende på om du har ssh-nycklar eller ej, dock är det att föredra att slippa skriva lösenord femtioelva gånger i minuten (se https://help.github.com/articles/adding-a-new-ssh-key-to-your-github-account/#platform-linux för hur du sätter upp ssh-keys om du inte har )

7. next
8. next
9. IMPORT AS GENERAL PROJECT <-- viktigt, annars riskerar eclipse att bli glömsk och till exempel glömma bort vad String är, och det vill du inte!
10. next
11. finish
