/*
 * github repository:  https://github.com/RogerPugaRuiz/Au_Usuarios.git
 */
package Main;

import DNATools.DNATools;
import PersistentData.Json;
import PersistentData.Bin;
import PrivateToken.PrivateToken;
import java.util.Random;
import java.util.Scanner;
import static Main.Menus.*;
import Exceptions.*;
import Users.Usuario;
import Users.Usuarios;

/**
 * Main class.
 *
 * @author roger
 */
public class Main {

    /**
     * Users Array.
     */
    public static Usuarios usuarios = new Usuarios();

    /**
     * All errors
     */
    public static final String[] ERRORCOMPRENSION = {
        "No te entiendo, puedes repetir que opcion prefieres",
        "Intenta con algo diferente como un numero",
        "Lo siento pero esto esta fuera de mi programacion"};

    public static final String ADN = "AGTCGCCTGCTGCAGCTGGTCAATCAAAAGAAATGAAACTTGACGAATA";
    public static final String ARN = "AGUCGCCUGCUGCAGCUGGUCAAUCAAAAGAAAUGAAACUUGACGAAUA";

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {

        if (!run()) {
            System.out.println("Imposible ejecutar el programa");
        }
    }

    /**
     * Method to run the program
     *
     * @return true if the program no has problems
     */
    public static boolean run() {
        try {
            // load a date with json and bin file
            loadData();

            int option = 0;
            do {
                option = showMenu(MENU);
                switch (option) {
                    case 0: // exit
                        option = exit();
                        break;
                    case 1: // login
                        inputLogin();
                        break;
                }
            } while (option != 0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Method to load the data.
     */
    public static void loadData() {

        // read the users in the bin file and put in "usuarios" class
        Bin bin = new Bin();
        PrivateToken pt = new PrivateToken();
        bin.read(usuarios, pt.getToken());

        // Method to import with json
        jsonImport();

        // guardar los nuevos jugadores en archivo.bin
        bin.addList(usuarios, pt.getToken());

    }

    /**
     * Method to read a json and import users.
     */
    public static void jsonImport() {

        Scanner sc = new Scanner(System.in);
        System.out.println("¿Quieres importar usuarios de un archivo Json?(Si/No)");
        char s = sc.next().charAt(0);
        if (s == 'S' || s == 's') { // if you want to import new json users
            System.out.println("Nombre del archivo json(*No escriba la extension json*)");
            String jsonFile = sc.next() + ".json";
            try { // if json exist
                Json json = new Json();
                // add all json users to "usuarios"
                usuarios.addAll(json.jsonImport(jsonFile));
                System.out.printf("Usuarios importados de %s\n", jsonFile);
            } catch (NullPointerException ex) {
                System.out.printf("hay problemas con el archivo %s\n", jsonFile);
            }
        }

    }

    /**
     * Method to loop exit
     *
     * @return int 0, loop breaks when option is 0
     */
    public static int exit() {
        // save current status of Users
        Bin bin = new Bin();
        PrivateToken pt = new PrivateToken();
        try { // try save
            bin.addList(usuarios, pt.getToken());
        } catch (Exception ex) {
            System.out.println("Error al intentar salir, es posible que no se guarden todos los archivos");
        }
        return 0;
    }

    /**
     * Method to read a menu
     *
     * @param MENU //text
     * @return La opcion del usuario
     */
    public static int showMenu(String[] MENU) {
        for (int i = 0; i < MENU.length; i++) {
            menuFormat(i, MENU[i]);
        }
        return scan();
    }

    /**
     * Method to scan a new option
     *
     * @return int option
     */
    public static int scan() {
        //        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int option = -1;
        System.out.print("--> ");
        try {
            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();
        } catch (Exception e) {
            Random rand = new Random();
            System.out.println(ERRORCOMPRENSION[rand.nextInt(ERRORCOMPRENSION.length)]);
        }
        return option;
    }

    /**
     * Format menu method.
     *
     * @param i index list
     * @param menu option text
     */
    public static void menuFormat(int i, String menu) {
        System.out.printf("%d. %s\n", i, menu);
    }

    /**
     * Method to get the e-mail, and password.
     */
    public static void inputLogin() throws InvalidDNAException {
        try {
            final Scanner SCANNER = new Scanner(System.in);
            System.out.print("e-mail: ");
            String email = SCANNER.next();

            System.out.print("password: ");
            String password = SCANNER.next();

            login(email, password);
        } catch (LoginException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Method to validate if it's a correct user
     *
     * @param email
     * @param password
     * @throws LoginException not possible login
     */
    public static void login(String email, String password) throws LoginException, InvalidDNAException {
        try {
            Usuario loginUser = usuarios.login(email, password);
            System.out.println(loginUser.getAll());

            switch (loginUser.getRol()) {
                case "admin": // user is admin
                    admin(loginUser);
                    break;
                case "user": // only user
                    user(loginUser);
                    break;
            }

        } catch (java.lang.NullPointerException npe) {
            throw new LoginException("e-mail o contraseña incorrecta");
        }
    }

    /**
     * Method to administrate the admin session.
     *
     * @param loginUser if he is an admin or simply a user
     */
    public static void admin(Usuario loginUser) {
        int option;
        do {
            option = showMenu(MENUADMIN);
            switch (option) {
                case 0: // exit
                    forceExit();
                    break;
                case 1: // get user information
                    System.out.println(loginUser.getAll());
                    break;
                case 2: // Create, Read, Update and Delete Methods
                    crud();
                    break;
                case 3: // export with json file
                    jsonExport();
                    break;
                case 4: // import with json file

                    jsonImport();
                    break;
                case 5: // logout
                    option = exit();
                    break;
            }
        } while (option != 0);
    }

    /**
     * Method to administrate the user session.
     *
     * @param loginUser if he is an admin or simply a user
     */
    public static void user(Usuario loginUser) throws InvalidDNAException {
        int option = 0;
        do {
            option = showMenu(MENUUSER);
            switch (option) {
                case 0: // exit
                    forceExit();
                    break;
                case 1: // show info
                    System.out.println(loginUser.getAll());
                    break;
                case 2: // logout
                    option = exit();
                    break;
                case 3: // DNATools
                    DNAMethods();
            }
        } while (option != 0);
    }

    /**
     * Method to use a DNAtools class.
     */
    public static void DNAMethods() throws InvalidDNAException {
        int option;
        do {
            option = showMenu(MENUADN);
            switch (option) {
                case 0:
                    option = exit();
                    break;
                case 1:
                    reversedDNA();
                    //option = reversedDNA();
                    break;
                case 2:
                    mostRepeatedBase();
                    break;
                case 3:
                    leastRepeatedBase();
                    break;
                case 4:
                    showBases();
                    break;
                case 5:
                    dnaToRna();
                    break;
                case 6:
                    rnaToDna();
                    break;
                case 7:
                    System.out.println("Introdueix Cadena ADN");
                    Scanner sc = new Scanner(System.in);
                    String cad = sc.next();
                    String cadena = cad.toUpperCase();
                    lengthADN(cadena);
            }
        } while (option != 0);
    }

    // ============================== USER MEHTODS =====================================
    private static void reversedDNA() throws InvalidDNAException {
        System.out.println("Cadena ADN: " + ADN);
        DNATools rev = new DNATools();
        String cadena = rev.reversed(ADN);
        System.out.println("Volta ADN: " + cadena);
    }

    private static void mostRepeatedBase() {
        System.out.println("Cadena ADN: " + ADN);
        DNATools most = new DNATools();
        char caracter = most.baseMesRepetida(ADN);
        System.out.println("El caracter de la cadena que mes repeteix es: " + caracter);
    }

    private static void leastRepeatedBase() {
        System.out.println("Cadena ADN: " + ADN);
        DNATools least = new DNATools();
        char caracter = least.baseLeastRepeated();
        System.out.println("El caracter de la cadena que menys es repeteix es: " + caracter);
    }

    private static void showBases() {
        System.out.println("Cadena ADN: " + ADN);
        DNATools show = new DNATools();
        show.seeBases(ADN);
    }

    private static void dnaToRna() {
        System.out.println("Cadena ADN: " + ADN);
        DNATools toRNA = new DNATools();
        String cadena = toRNA.convert(ADN, 0);
        System.out.println("ADN to ARN: " + cadena);

    }

    private static void rnaToDna() {
        System.out.println("Cadena ARN: " + ARN);
        DNATools toRNA = new DNATools();
        String cadena = toRNA.convert(ARN, 1);
        System.out.println("ARN to ADN: " + cadena);
    }

    private static void lengthADN(String cadena) {
        System.out.println("Cadena ADN: " + cadena);
        DNATools len = new DNATools();
        int num = 0;
        num = len.longitudCadenaADN(cadena);
        System.out.println("La longitud de la cadena d'ADN es de " + num + " carcaters");
    }

    // ============================== ADMIN METHODS ====================================
    /**
     * Method to export encrypt json
     */
    public static void jsonExport() {
        final Scanner SCANNER = new Scanner(System.in);
        System.out.println("(¡NO ES NECESARIO AÑADIR LA EXTENSION!)\nnombre del archivo json: ");
        Json json = new Json();
        json.jsonExport(SCANNER.next() + ".json", usuarios);
    }

    /**
     * Method to control de crud options
     */
    public static void crud() {
        int option;
        do {
            option = showMenu(CRUD);
            switch (option) {
                case 0: // exit
                    option = exit();
                    break;
                case 1: 
                    try {
                    // create new User
                    create();
                } catch (NameLastNameException ex) {
                    System.out.println(ex.getMessage());
                }
                break;

                case 2: // read with diferents options
                    read();
                    break;
                case 3: // user update
                    update();
                    break;
                case 4: // user delete
                    delete();
                    break;

            }
        } while (option != 0);
    }

    /**
     * Method to control the update options.
     */
    public static void update() {
        int option;
        do {
            option = showMenu(ATTRIBUTES);
            switch (option) {
                case 0:
                    option = exit();
                    break;
                case 1:
                    updateName();
                    break;
                case 2:
                    updateLastname();
                    break;
                case 3:
                    updatePassword();
                    break;
                case 4:
                    updateRol();
                    break;
            }

        } while (option != 0);
    }

    /**
     * Method to update the name.
     */
    public static void updateName() {
        final Scanner SC = new Scanner(System.in);
        System.out.print("Nombre: ");
        String nombre = SC.next();

        System.out.print("E-mail: ");
        String email = SC.next();
        Usuario u = usuarios.search(email);

        u.setNombre(nombre);
    }

    /**
     * Method to update the last name.
     */
    public static void updateLastname() {
        final Scanner SC = new Scanner(System.in);
        System.out.print("Apellidos: ");
        String lastname = SC.nextLine();

        System.out.println("E-mail: ");
        String email = SC.next();
        Usuario u = usuarios.search(email);

        u.setApellidos(lastname);
    }

    /**
     * Method to update the password.
     */
    public static void updatePassword() {
        final Scanner SC = new Scanner(System.in);
        System.out.print("Password: ");
        String password = SC.next();

        System.out.print("E-mail: ");
        String email = SC.next();
        Usuario u = usuarios.search(email);

        u.setPassword(password);
    }

    /**
     * Method to update de role.
     */
    public static void updateRol() {
        final Scanner SC = new Scanner(System.in);
        System.out.print("Rol: ");
        String rol = SC.next();

        System.out.print("E-mail: ");
        String email = SC.next();
        Usuario u = usuarios.search(email);
        if (rol.equalsIgnoreCase("admin")) {
            u.setRol(Usuario.ADMIN);
        } else {
            u.setRol(Usuario.USER);
        }

    }

    /**
     * Method to delete user.
     */
    public static void delete() {
        final Scanner SCANNER = new Scanner(System.in);

        System.out.print("E-mail: ");

        String s = SCANNER.nextLine();
        s = s.replace(" ", "");
        String[] emails = s.split(",");
        for (String email : emails) {
            Usuario usuario = usuarios.search(email);
            System.out.printf("Estas intentando eliminar a %s %s, estas  seguro (Si,No)\n", usuario.getNombre(), usuario.getApellidos());
            char option = SCANNER.next().charAt(0);
            if (Character.toLowerCase(option) == 's' || Character.toLowerCase(option) == 'y') {
                if (usuarios.remove(usuario)) {
                    System.out.printf("%s %s fue eliminado permanentemente\n", usuario.getNombre(), usuario.getApellidos());
                } else {
                    System.out.printf("%s $s no existe como usuario\n", usuario.getNombre(), usuario.getApellidos());
                }
            } else {
                System.out.printf("%s %s no fue eliminado por no confirmar la operación\n", usuario.getNombre(), usuario.getApellidos());
            }
        }

    }

    /**
     * Method to read.
     */
    public static void read() {
        final Scanner SCANNER = new Scanner(System.in);

        int option = -1;
        do {
            try {
                option = showMenu(READ);
                switch (option) {
                    case 0:
                        option = exit();
                        break;
                    case 1:
                        System.out.println(usuarios.getAll());
                        System.out.printf("Numero de usuarios: %d\n", usuarios.count());
                        break;
                    case 2:
                        System.out.print("E-mail: ");
                        String email = SCANNER.next();
                        System.out.println(usuarios.search(email).getAll());
                        break;
                    case 3:
                        System.out.print("Nombre: ");
                        String nombre = SCANNER.next();
                        Usuarios userSearch = usuarios.searchAll(nombre);
                        System.out.println(userSearch.getAll());
                        System.out.printf("Numero de usuarios: %d encontrados\n", userSearch.count());
                        break;
                    case 4:
                        System.out.print("Nombre: ");
                        nombre = SCANNER.next();
                        System.out.print("Apellido: ");
                        SCANNER.nextLine();
                        String apellido = SCANNER.nextLine();
                        userSearch = usuarios.searchAll(nombre, apellido);
                        System.out.println(userSearch.getAll());
                        System.out.printf("Numero de usuario: %d encontrados\n", userSearch.count());
                        break;

                }
            } catch (UserAlreadyExistException ex) {
                System.out.println(ex.getMessage());
            }
        } while (option != 0);

    }

    /**
     * Method to create.
     */
    public static void create() throws NameLastNameException {
        final Scanner SCANNER = new Scanner(System.in);

        String nombre;
        String apellidos;
        String email;
        String password;
        int rol;

        System.out.println("nombre: ");
        nombre = SCANNER.next();

        System.out.println("apellido: ");
        SCANNER.nextLine(); // para poder capturar una linea con espacios
        apellidos = SCANNER.nextLine();

        System.out.println("email: ");
        email = SCANNER.next();

        System.out.println("password: ");
        password = SCANNER.next();

        System.out.println("rol(user/admin): ");
        String rolStr = SCANNER.next();
        rol = Usuario.USER;
        if (rolStr.equalsIgnoreCase("admin")) {
            rol = Usuario.ADMIN;
        }

        if (containNumbers(nombre)) {
            throw new NameLastNameException("Nombre no puede contener numeros");
        } else if (containNumbers(apellidos)) {
            throw new NameLastNameException("Apellido no puede contener numeros");
        } else {
            try {
                usuarios.add(new Usuario(nombre, apellidos, email, password, rol));
            } catch (UserAlreadyExistException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Method to force exit, the program die.
     */
    public static void forceExit() {
        // guardar los usuarios en archivos.bin y cerrar la aplicación
        Bin bin = new Bin();
        PrivateToken pt = new PrivateToken();
        bin.addList(usuarios, pt.getToken());
        System.exit(0);
    }

    /**
     * Method to check if str contain a number
     *
     * @param str
     * @return boolean true if contain numbers else false
     */
    private static boolean containNumbers(String str) {
        return str.contains("1")
                || str.contains("2")
                || str.contains("3")
                || str.contains("4")
                || str.contains("5")
                || str.contains("6")
                || str.contains("7")
                || str.contains("8")
                || str.contains("9")
                || str.contains("0");
    }
}
