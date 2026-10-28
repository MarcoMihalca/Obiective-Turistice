package ro.utcn.obiectiveturistice.data

/**
 * Sursa unică de date a aplicației (in-memory repository).
 * Conține 12 obiective turistice din Maramureș (câte 4 în fiecare categorie: Muzee, Sport, Restaurante).
 */
object PlacesRepository {

    private val places = listOf(
        // ==================== MUZEE (4 obiective) ====================
        Place(
            id = 1,
            name = "Muzeul Satului Maramureșean",
            category = PlaceCategory.MUZEE,
            location = "Sighetu Marmației",
            description = "Muzeu etnografic în aer liber situat pe Dealul Dobăieș, inaugurat în 1981. Reunește peste 30 de gospodării tradiționale din lemn autentice din toate subzonele istorice ale Maramureșului (Cosău-Mara, Iza, Vișeu-Borșa și Țara Chioarului), grupate în jurul bisericii de lemn din Oncești (sec. al XVI-lea)."
        ),
        Place(
            id = 2,
            name = "Memorialul Victimelor Comunismului și al Rezistenței",
            category = PlaceCategory.MUZEE,
            location = "Sighetu Marmației",
            description = "Amenajat în fosta închisoare politică din Sighetu Marmației (construită în 1897), memorialul este un ansamblu muzeal de referință europeană dedicat memoriei demnitarilor, oamenilor de cultură și cetățenilor întemnițați în perioada regimului comunist. Fiecare fostă celulă găzduiește o sală tematică documentară."
        ),
        Place(
            id = 3,
            name = "Muzeul Județean de Istorie și Arheologie Maramureș",
            category = PlaceCategory.MUZEE,
            location = "Baia Mare",
            description = "Găzduit în clădirea istorică a fostei Monetării Imperiale din Baia Mare (sec. al XVIII-lea), muzeul prezintă evoluția regiunii din preistorie până în epoca modernă. Include colecții valoroase de arheologie din epoca bronzului, istoria mineritului aurifer băimărean și vechiul mecanism al ceasului din Turnul Ștefan."
        ),
        Place(
            id = 4,
            name = "Muzeul de Mineralogie „Victor Gorduza”",
            category = PlaceCategory.MUZEE,
            location = "Baia Mare",
            description = "Cel mai mare muzeu regional de mineralogie din Europa, renumit pentru colecția sa impresionantă de peste 16.000 de eșantioane minerale și celebrele „flori de mină” extrase din zăcămintele hidrotermale din bazinul minier Baia Mare, Cavnic, Baia Sprie și Herja."
        ),

        // ==================== SPORT (4 obiective) ====================
        Place(
            id = 5,
            name = "Domeniul schiabil Cavnic",
            category = PlaceCategory.SPORT,
            location = "Cavnic",
            description = "Situat la poalele Munților Gutâi, domeniul schiabil din Cavnic (zonele Icoana și Roata) oferă peste 8 km de pârtii omologate pentru toate nivelurile de dificultate, instalații de telescaun și teleschi, nocturnă, snowpark și condiții excelente pentru sporturile de iarnă din noiembrie până în primăvară."
        ),
        Place(
            id = 6,
            name = "Stațiunea Borșa (schi, drumeții)",
            category = PlaceCategory.SPORT,
            location = "Borșa",
            description = "Important centru al sporturilor montane situat între Munții Rodnei și Munții Maramureșului. Dispune de telegondolă modernă și pârtie olimpică de schi, precum și de trasee spectaculoase de drumeție către Cascada Cailor — cea mai înaltă cascadă din România, cu o cădere de apă de 90 de metri."
        ),
        Place(
            id = 7,
            name = "Parcul Național Munții Rodnei (trasee montane)",
            category = PlaceCategory.SPORT,
            location = "Borșa / Moisei",
            description = "Rezervație a biosferei UNESCO ce adăpostește cele mai înalte culmi din Carpații Orientali: Vârful Pietrosul Rodnei (2.303 m) și Vârful Ineu (2.279 m). Oferă numeroase trasee montane marcate pentru drumeții și alpinism, lacuri glaciare (Lacul Iezer, Lacul Lala) și peisaje alpine spectaculoase."
        ),
        Place(
            id = 8,
            name = "Stațiunea Izvoare și Vârful Igniș (drumeții, schi)",
            category = PlaceCategory.SPORT,
            location = "Baia Mare / Desești",
            description = "Platoul vulcanic Izvoare (altitudine ~920 m), aflat în apropiere de Baia Mare, este destinația ideală în orice anotimp pentru drumeții montane spre Vârful Igniș (1.307 m) și Cheile Tătarului, ture de ciclism montan prin pădurile seculare de fag și schi pe pârtiile Poiana Soarelui și Cora."
        ),

        // ==================== RESTAURANTE (4 obiective) ====================
        Place(
            id = 9,
            name = "Restaurant Castel Transilvania",
            category = PlaceCategory.RESTAURANTE,
            location = "Baia Mare",
            description = "Restaurant cu arhitectură de inspirație medievală și panoramă asupra orașului Baia Mare, apreciat pentru preparatele tradiționale ardelenești și maramureșene gătite după rețete autentice, platouri calde, fripturi la jar și deserturi de casă într-o ambianță rustică și primitoare."
        ),
        Place(
            id = 10,
            name = "Păstrăvăria Alex",
            category = PlaceCategory.RESTAURANTE,
            location = "Mara (Desești)",
            description = "Amplasată într-un cadru natural pitoresc la poalele Pasului Gutâi, pe valea râului Mara, păstrăvăria este celebră pentru păstrăvul proaspăt pescuit direct din bazinele proprii cu apă rece de munte, servit cu mămăligă aburindă, mujdei și plăcinte crețe maramureșene."
        ),
        Place(
            id = 11,
            name = "Casa Iurca de Călinești",
            category = PlaceCategory.RESTAURANTE,
            location = "Sighetu Marmației",
            description = "Găzduit într-o clădire monument istoric din centrul vechi al Sighetului, ce a aparținut unei vechi familii nobiliare maramureșene, restaurantul oferă bucate tradiționale locale autentice: balmoș ciobănesc, hribi cu smântână, sarmale în foi de varză și papanași cu dulceață de afine."
        ),
        Place(
            id = 12,
            name = "Restaurant Bizo (Centrul Istoric)",
            category = PlaceCategory.RESTAURANTE,
            location = "Baia Mare",
            description = "Situat în Piața Libertății (Centrul Vechi din Baia Mare), în clădiri medievale restaurate, localul îmbină bucătăria locală transilvăneană cu preparate internaționale, fiind un loc de popas îndrăgit de turiști după vizitarea Turnului Ștefan și a centrului istoric."
        )
    )

    /**
     * Returnează lista completă a obiectivelor turistice.
     */
    fun getAll(): List<Place> = places

    /**
     * Returnează obiectivele filtrate după categorie (sau toate dacă [category] este null).
     */
    fun getByCategory(category: PlaceCategory?): List<Place> {
        return if (category == null) {
            places
        } else {
            places.filter { it.category == category }
        }
    }

    /**
     * Caută și returnează un obiectiv turistic după [id], sau null dacă nu există.
     */
    fun getById(id: Int): Place? = places.find { it.id == id }

    /**
     * Returnează lista obiectivelor ale căror ID-uri se regăsesc în [favoriteIds].
     */
    fun getFavorites(favoriteIds: Set<Int>): List<Place> {
        return places.filter { it.id in favoriteIds }
    }
}
