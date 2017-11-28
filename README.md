# tranfomCsvtoXmlMetadata

EJI

- Se utiliza el programa que se encuentra en el archivo tranfomCsvtoXmlMetadata-master.zip, el cual se ejecuta con NetBeans.

- A partir de los archivos excel recibidos, se hace un análisis de los valores que se encuentran en las columnas área y subárea
  y se crea la estructura correspondiente en DSpace (usualmente muy similar a las anteriores).

- Se genera un archivo csv para el conjunto de ítems de igual valor dentro de la clasificación de subárea del excel (se aplica un filtro)
  (correspondiente a una subcomunidad--> colección de la estructura creada anteriormente),
  los csv van separados por "&" y sin la primer fila con el nombre de los campos, el archivo se debe llamar encuentroji.csv, hay que
  tener cuidado de que se genere el archivo csv sólo con lo que corresponde a esa colección (lo que se filtra: ej: "Biología").

- Se tiene que ir haciendo un archivo csv por vez, luego copiarlo en la carpeta EncuentroJI.

- Los archivos pdf correspondientes a cada csv se copian en el directorio allFile3.

- Debe estar creada la carpeta DirectorioItems que es sólo para uso del programa.

- Se va corriendo el programa para cada par de 1) archivo csv y 2)archivos pdf asociados.
  El zip generado se carga en la colección correspondiente y antes de volver a correr el programa se borran:
  el zip, el archivo csv previo y los archivos pdf que están en la carpeta allFile3.


- Cuando la ejecución del programa tiró error se encontraron los siguientes problemas:

  > mal generado el csv porque no tenía todos los campos que el programa espera,
    hubo casos que el excel venía incompleto para alguna subárea.

  > Otro error posible es que estén mal los nombres de archivos pdf, con espacios, sin un punto o número, etc.
