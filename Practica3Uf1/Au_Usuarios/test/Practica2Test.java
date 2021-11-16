

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */



import Encryption.EncryptAndDecrypt;
import PrivateToken.PrivateToken;
import DNATools.DNATools;
import Exceptions.InvalidDNAException;
import Users.Usuario;
import Users.Usuarios;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author roger
 */
public class Practica2Test {
    public Practica2Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void loginTest(){
        try {
            Usuarios users = new Usuarios();
            final Usuario EXPECTED_USER = new Usuario(
                    "Roger",
                    "Puga",
                    "roger@gmail.com",
                    "1234", Usuario.USER);
            users.add(EXPECTED_USER);
            Usuario user_login = users.login("roger@gmail.com", "1234");
            Boolean condition = user_login.equals(EXPECTED_USER);
            assertTrue("Login Correcto",condition);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    @Test
    public void loginFailTest(){
                try {
            Usuarios users = new Usuarios();
            final Usuario EXPECTED_USER = new Usuario(
                    "Roger",
                    "Puga",
                    "roger@gmail.com",
                    "1234", Usuario.USER);
            users.add(EXPECTED_USER);
            Usuario user_login = users.login("roger@gmail.com", "123");
            Boolean condition = user_login == null;
            assertTrue("Fail login",condition);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }


    @Test
    public void encryptTest(){
        try {
            String key = "holalskdjflksjdflksjdlfkjsdkjf";
            String expected = "hola mundo";
            EncryptAndDecrypt ead = new EncryptAndDecrypt();
            String encrypt = ead.encrypt(expected, key);
            String decrypt = ead.decrypt(encrypt, key);
            
            assertTrue("encrypt correct", expected.equals(decrypt));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void encryptFailTest(){
                try {
            String key = "holalskdjflksjdflksjdlfkjsdkjf";
            String expected = "hola mundo";
            EncryptAndDecrypt ead = new EncryptAndDecrypt();
            String encrypt = ead.encrypt(expected, "holalskdjflksjdflksjdlf");
            String decrypt = ead.decrypt(encrypt, key);
            
            assertFalse("encrypt incorrect", expected.equals(decrypt));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Test
    public void generateTokenTest(){
        EncryptAndDecrypt ead = new EncryptAndDecrypt();
        assertTrue("La longitud de la clave es la correcta", ead.getKey().length() == 100);
    }
    
    @Test
    public void privateTokenTest(){
        PrivateToken pt = new PrivateToken();
        String key = pt.getToken();
        String repeatKey = pt.getToken();
        assertTrue("Clave privada correcta",key.equals(repeatKey));
    }
    
    @Test
    public void ReversedAdnTest() {
        try {
            DNATools tools = new DNATools();
            String adn = "agcc";
            String expectedAdn = "CCGA";
            String new_adn = tools.reversed(adn);
            assertTrue("ADN",expectedAdn.equals(new_adn));

        } catch (InvalidDNAException ex) {
            fail(ex.getMessage());
        }
    }
    @Test
    public void ReversedAdnFailTest(){
                try {
            DNATools tools = new DNATools();
            String adn = "agcc";
            String expectedAdn = "CCGA";
            String new_adn = tools.reversed("AGCT");
            assertFalse("ADN no valido",expectedAdn.equals(new_adn));

        } catch (InvalidDNAException ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void IsValidAdnTest(){
        String valid_adn = "AGCT";
        DNATools tools = new DNATools();
        
        for (int i=0; i<valid_adn.length(); i++){
            assertTrue(tools.isValidAdn(valid_adn.charAt(i)));
        }        
    }
    
    @Test
    public void IsValidAdnFailTest(){
        String valid_adn = "aa1234asdf";
        DNATools tools = new DNATools();
        
        for (int i=0; i<valid_adn.length(); i++){
            assertFalse(tools.isValidAdn(valid_adn.charAt(i)));
        }        
    }
    

    @Test
    public void BaseMostRepetedTest() {
        String cadena = "AGTCCCC";
        DNATools tools = new DNATools();
        char expResult = 'C';
        
        char result = tools.baseMostRepeted(cadena);
        assertEquals(expResult, result);
    }
    
    @Test
    public void ConvertTest() {
        String cadenaADN= "AGTTC";
        DNATools tools = new DNATools();
        String expResult = "AGUUC";
        int i = 0;
 
        String result = tools.convert(cadenaADN, i);
        assertEquals(expResult, result);
    }
    

}



