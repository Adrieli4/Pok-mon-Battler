I detta program får du redigera ditt eget pokedex (lägga till, ta bort och redigera Pokemon). Utöver det kan du starta 
strider mot datorn som har sitt eget pokedex. I en strid ska du välja ett Pokemon ur ditt pokedex och datorn väljer ett 
från sitt och de Pokemonen hamnar i strid mot varandra. Det finns en klass "DamageAttack" som ärver från "Attack". 
DamageAttack har allt Attack har plus power. Power är till för att beräkna hur stor skadan blir på motsåndarens pokemon 
genom att ta hänsyn till basskadan plus typen på attacken och motståndaren samt träffsäkerheten. DamageAttack 
impplementerar interfacet "BattleAction". På det sättet kan namnet på attacken implementeras, för att specificera vilken
attack som används, och träffsäkerheten plus typen för att beräkna skadan (power). De VG-kraven jag hör försökt uppfylla 
är typeffektivitet och polymorfism. Attackens effekt fördubblas om attacken har typen fire och motståndaren grass, 
attacken water och motståndaren fire eller om motståndare som är water attackeras av grass eller electric. Om attacken 
är fire och motståndaren water, attacken water och motståndaren grass eller attacken grass och motståndaren fire 
halveras den. Annars ändras inte skadan. Sedan finns träffsäkerheten vilket är ett nummer mellan 30 och 100. I 
programmet genereras ett nummer mellan 1 och 100, och om det nummret är lägre eller lika med träffsäkerheten, då lyckas 
Pokemonet attackera sin motståndare. Annars missar den.