/* 
 * Funcion que me permite normalizar el texto de los campos de la base de datos para buscar sin importar acentos o tildes
 */
CREATE FUNCTION TEXTO_ESTANDAR(texto VARCHAR(5000)) RETURNS VARCHAR(5000) 
    PARAMETER STYLE JAVA NO SQL LANGUAGE JAVA 
    EXTERNAL NAME 'ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesDerby.normalizarTextoDerby'
