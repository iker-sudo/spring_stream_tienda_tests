package org.iesvdm.tienda;

import org.iesvdm.tienda.modelo.Fabricante;
import org.iesvdm.tienda.modelo.Producto;
import org.iesvdm.tienda.repository.FabricanteRepository;
import org.iesvdm.tienda.repository.ProductoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.joining;

@SpringBootTest
class TiendaApplicationTests {

    @Autowired
    FabricanteRepository fabRepo;

    @Autowired
    ProductoRepository prodRepo;

    @Test
    void testAllFabricante() {
        var listFabs = fabRepo.findAll();

        listFabs.forEach(f -> {
            System.out.println(">>" + f + ":");
            f.getProductos().forEach(System.out::println);
        });
    }

    @Test
    void testAllProducto() {
        var listProds = prodRepo.findAll();

        listProds.forEach(p -> {
            System.out.println(">>" + p + ":" + "\nProductos mismo fabricante " + p.getFabricante());
            p.getFabricante().getProductos().forEach(pF -> System.out.println(">>>>" + pF));
        });

    }

    /**
     * 1. Lista los nombres y los precios de todos los productos de la tabla
     * producto
     */
    @Test
    void test1() {
        var listProds = prodRepo.findAll();

        // TODO
    }

    /**
     * 2. Devuelve una lista de Producto completa con el precio de euros convertido
     * a dólares .
     */
    @Test
    void test2() {
        var listProds = prodRepo.findAll();

        var listPrecios = listProds.stream()
                .map(p -> p.getPrecio() * 1.08) // Double con precisión
                .map(prec -> BigDecimal.valueOf(prec).setScale(2, RoundingMode.HALF_UP)) // BigDecimal con 2 decimales
                .map(prec -> prec + " $") // String con el símbolo de $
                .toList();

        listPrecios.forEach(s -> System.out.println(s));
        // TODO
    }

    /**
     * 3. Lista los nombres y los precios de todos los productos, convirtiendo los
     * nombres a mayúscula.
     */
    @Test
    void test3() {
        var listProds = prodRepo.findAll();

        List<String> listResultado = listProds.stream()// Producto
                .map(p -> p.getNombre().toUpperCase() + " precio:" + p.getPrecio()) // String
                .toList();

        listResultado.forEach(s -> System.out.println(s));

        Assertions.assertEquals(9, listResultado.size());
        // TODO
    }

    /**
     * 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas
     * los dos primeros caracteres del nombre del fabricante.
     */
    @Test
    void test4() {
        var listFabs = fabRepo.findAll();

        List<String> listResultado = listFabs.stream() //Fabricante
                .map(f -> f.getNombre() + " " + f.getNombre().substring(0, 2).toUpperCase()) //String
                .toList();

        listResultado.forEach(s -> System.out.println(s));

        Assertions.assertEquals(9, listResultado.size());//Comprueba que el test pasa

        // TODO
    }

    /**
     * 5. Lista el código de los fabricantes que tienen productos.
     */
    @Test
    void test5() {
        var listFabs = fabRepo.findAll();
        List<Integer> listCodigo = listFabs.stream()
                .filter(fabricante -> fabricante.getProductos() != null && fabricante.getProductos().size() > 0)
                .map(c -> c.getCodigo())
                .toList();
        listCodigo.forEach(c -> System.out.println(c));
        // TODO
    }

    /**
     * 6. Lista los nombres de los fabricantes ordenados de forma descendente.
     */
    @Test
    void test6() {
        var listFabs = fabRepo.findAll();
        var listaOrdenada = listFabs.stream()
                .sorted(comparing((Fabricante f) -> f.getNombre(), reverseOrder()))
                .map(f -> f.getNombre())
                .toList();
        listaOrdenada.forEach(x -> System.out.println(x));

        // TODO
    }

    /**
     * 7. Lista los nombres de los productos ordenados en primer lugar por el nombre
     * de forma ascendente y en segundo lugar por el precio de forma descendente.
     */
    @Test
    void test7() {
        var listProds = prodRepo.findAll();

        var listaAscDesc = listProds.stream()
                .sorted(comparing((Producto p) -> p.getNombre())
                        .thenComparing((Producto p) -> p.getPrecio(), reverseOrder()))
                .map(p -> p.getNombre() + " " + p.getPrecio())
                .toList();
        listaAscDesc.forEach(x -> System.out.println(x));
        Assertions.assertEquals(9, listaAscDesc.size());

        // TODO
    }

    /**
     * 8. Devuelve una lista con los 5 primeros fabricantes.
     */
    @Test
    void test8() {
        var listFabs = fabRepo.findAll();

        var listaCincoPrimeros = listFabs.stream()
                .limit(5)
                .map(f -> f.getNombre())
                .toList();
        listaCincoPrimeros.forEach(x -> System.out.println(x));
        Assertions.assertEquals(5, listaCincoPrimeros.size());

        // TODO
    }

    /**
     * 9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El
     * cuarto fabricante también se debe incluir en la respuesta.
     */
    @Test
    void test9() {
        var listFabs = fabRepo.findAll();

        var listaSkipFabricantes = listFabs.stream()
                .skip(4)
                .limit(2)
                .map(f -> f.getNombre())
                .toList();
        listaSkipFabricantes.forEach(x -> System.out.println(x));
        Assertions.assertEquals(2, listaSkipFabricantes.size());
        // TODO
    }

    /**
     * 10. Lista el nombre y el precio del producto más barato
     */
    @Test
    void test10() {
        var listProds = prodRepo.findAll();

        var listaBarato = listProds.stream()
                .sorted(comparing(Producto::getPrecio))
                .limit(1)
                .map(p -> p.getNombre() + " " + p.getPrecio())
                .toList();
        listaBarato.forEach(x -> System.out.println(x));
        Assertions.assertEquals(1, listaBarato.size());

        // TODO
    }

    /**
     * 11. Lista el nombre y el precio del producto más caro
     */
    @Test
    void test11() {
        var listProds = prodRepo.findAll();

        var listaCaro = listProds.stream()
                .sorted(comparing(Producto::getPrecio, reverseOrder()))
                .limit(1)
                .map(p -> p.getNombre() + " " + p.getPrecio())
                .toList();
        listaCaro.forEach(x -> System.out.println(x));
        Assertions.assertEquals(1, listaCaro.size());
        // TODO
    }

    /**
     * 12. Lista el nombre de todos los productos del fabricante cuyo código de
     * fabricante es igual a 2.
     *
     */
    @Test
    void test12() {
        var listProds = prodRepo.findAll();

        var listaCodigo = listProds.stream()
                .filter(p -> p.getFabricante().getCodigo() == 2)
                .map(p -> p.getNombre())
                .toList();
        listaCodigo.forEach(x -> System.out.println(x));

        Assertions.assertEquals(2, listaCodigo.size());

        // TODO
    }

    /**
     * 13. Lista el nombre de los productos que tienen un precio menor o igual a
     * 120€.
     */
    @Test
    void test13() {
        var listProds = prodRepo.findAll();

        var listPrecio = listProds.stream()
                .filter(p -> p.getPrecio() <= 120)
                .map(p -> p.getNombre() + " " + p.getPrecio())
                .toList();
        listPrecio.forEach(x -> System.out.println(x));
        Assertions.assertEquals(3, listPrecio.size());
        // TODO
    }

    /**
     * 14. Lista los productos que tienen un precio mayor o igual a 400€.
     */
    @Test
    void test14() {
        var listProds = prodRepo.findAll();

        var listPrecio = listProds.stream()
                .filter(p -> p.getPrecio() >= 400)
                .map(p -> p.getNombre() + " " + p.getPrecio())
                .toList();
        listPrecio.forEach(x -> System.out.println(x));
        Assertions.assertEquals(3, listPrecio.size());
        // TODO
    }

    /**
     * 15. Lista todos los productos que tengan un precio entre 80€ y 300€.
     */
    @Test
    void test15() {
        var listProds = prodRepo.findAll();

        var listaPrecioEntre = listProds.stream()
                .filter(p -> p.getPrecio() >= 80 && p.getPrecio() <= 300)
                .map(p -> p.getNombre() + " " + p.getPrecio())
                .toList();
        listaPrecioEntre.forEach(x -> System.out.println(x));
        Assertions.assertEquals(6, listaPrecioEntre.size());
        // TODO
    }

    /**
     * 16. Lista todos los productos que tengan un precio mayor que 200€ y que el
     * código de fabricante sea igual a 6.
     */
    @Test
    void test16() {
        var listProds = prodRepo.findAll();

        var listaPrecioYcodigo = listProds.stream()
                .filter(p -> p.getPrecio() > 200 && p.getFabricante().getCodigo() == 6)
                .map(p -> p.getNombre() + " " + p.getPrecio())
                .toList();
        listaPrecioYcodigo.forEach(x -> System.out.println(x));
        Assertions.assertEquals(1, listaPrecioYcodigo.size());


        // TODO
    }

    /**
     * 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5
     * utilizando un Set de codigos de fabricantes para filtrar.
     */
    @Test
    void test17() {
        var listProds = prodRepo.findAll();
        List<Integer> codigos = Arrays.asList(1, 3, 5);

        var listaCodigos = listProds.stream()
                .filter(p -> codigos.contains(p.getFabricante().getCodigo()))
                .map(p -> p.getNombre() + " " + p.getPrecio())
                .toList();
        listaCodigos.forEach(x -> System.out.println(x));
        Assertions.assertEquals(5, listaCodigos.size());
        // TODO
    }

    /**
     * 18. Lista el nombre y el precio de los productos en céntimos.
     */
    @Test
    void test18() {
        var listProds = prodRepo.findAll();
        var listaCentimos = listProds.stream()
                .map(p -> p.getNombre() + " " + (p.getPrecio() * 100) + " céntimos")
                .toList();
        listaCentimos.forEach(x -> System.out.println(x));
        Assertions.assertEquals(9, listaCentimos.size());
        // TODO
    }

    /**
     * 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
     */
    @Test
    void test19() {
        var listFabs = fabRepo.findAll();
        var listaEmpiezaS = listFabs.stream()
                .filter(f -> f.getNombre().startsWith("S"))
                .map(f -> f.getNombre())
                .toList();
        listaEmpiezaS.forEach(x -> System.out.println(x));
        Assertions.assertEquals(2, listaEmpiezaS.size());
        // TODOS
    }

    /**
     * 20. Devuelve una lista con los productos que contienen la cadena Portátil en
     * el nombre.
     */
    @Test
    void test20() {
        var listProds = prodRepo.findAll();
        var listaPortatil = listProds.stream()
                .filter(p -> p.getNombre().contains("Portátil"))
                .map(p -> p.getNombre() + " " + p.getPrecio())
                .toList();
        listaPortatil.forEach(x -> System.out.println(x));
        Assertions.assertEquals(2, listaPortatil.size());
        // TODO
    }

    /**
     * 21. Devuelve una lista con el nombre de todos los productos que contienen la
     * cadena Monitor en el nombre y tienen un precio inferior a 215 €.
     */
    @Test
    void test21() {
        var listProds = prodRepo.findAll();
        var listaMonitor = listProds.stream()
                .filter(p -> p.getNombre().contains("Monitor") && p.getPrecio() < 215)
                .map(p -> p.getNombre() + " " + p.getPrecio())
                .toList();
        listaMonitor.forEach(x -> System.out.println(x));
        Assertions.assertEquals(2, listaMonitor.size());
        // TODO
    }

    /**
     * 22. Lista el nombre y el precio de todos los productos que tengan un precio
     * mayor o igual a 180€.
     * Ordene el resultado en primer lugar por el precio (en orden descendente) y en
     * segundo lugar por el nombre (en orden ascendente).
     */
    void test22() {
        var listProds = prodRepo.findAll();
        var listaPrecio180 = listProds.stream()
                .filter(p -> p.getPrecio() >= 180)
                .sorted(comparing(Producto::getPrecio, reverseOrder())
                        .thenComparing(Producto::getNombre))
                .map(p -> p.getNombre() + " " + p.getPrecio())
                .toList();
        listaPrecio180.forEach(x -> System.out.println(x));
        Assertions.assertEquals(6, listaPrecio180.size());
        // TODO
    }

    /**
     * 23. Devuelve una lista con el nombre del producto, precio y nombre de
     * fabricante de todos los productos de la base de datos.
     * Ordene el resultado por el nombre del fabricante, por orden alfabético.
     */
    @Test
    void test23() {
        var listProds = prodRepo.findAll();
        var listaNombreFab = listProds.stream()
                .sorted(comparing(p -> p.getFabricante().getNombre()))
                .map(p -> p.getNombre() + " " + p.getPrecio() + " " + p.getFabricante().getNombre())
                .toList();
        listaNombreFab.forEach(x -> System.out.println(x));
        Assertions.assertEquals(9, listaNombreFab.size());
        // TODO
    }

    /**
     * 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante,
     * del producto más caro.
     */
    @Test
    void test24() {
        var listProds = prodRepo.findAll();
        var listaCaro = listProds.stream()
                .sorted(comparing(Producto::getPrecio, reverseOrder()))
                .limit(1)
                .map(p -> p.getNombre() + " " + p.getPrecio() + " " + p.getFabricante().getNombre())
                .toList();
        listaCaro.forEach(x -> System.out.println(x));
        Assertions.assertEquals(1, listaCaro.size());
        // TODO
    }

    /**
     * 25. Devuelve una lista de todos los productos del fabricante Crucial que
     * tengan un precio mayor que 200€.
     */
    @Test
    void test25() {
        var listProds = prodRepo.findAll();
        var listaCrucial = listProds.stream()
                .filter(p -> p.getFabricante().getNombre().equals("Crucial") && p.getPrecio() > 200)
                .map(p -> p.getNombre() + " " + p.getPrecio() + " " + p.getFabricante().getNombre())
                .toList();
        listaCrucial.forEach(x -> System.out.println(x));
        Assertions.assertEquals(1, listaCrucial.size());
        // TODO
    }

    /**
     * 26. Devuelve un listado con todos los productos de los fabricantes Asus,
     * Hewlett-Packard y Seagate
     */
    @Test
    void test26() {
        var listProds = prodRepo.findAll();
        List<String> fabricantes = Arrays.asList("Asus", "Hewlett-Packard", "Seagate");

        var listaFabricantes = listProds.stream()
                .filter(p -> fabricantes.contains(p.getFabricante().getNombre()))
                .map(p -> p.getNombre() + " " + p.getPrecio() + " " + p.getFabricante().getNombre())
                .toList();
        listaFabricantes.forEach(x -> System.out.println(x));
        Assertions.assertEquals(5, listaFabricantes.size());
        // TODO
    }

    /**
     * 27. Devuelve un listado con el nombre de producto, precio y nombre de
     * fabricante, de todos los productos que tengan un precio mayor o igual a 180€.
     * Ordene el resultado en primer lugar por el precio (en orden descendente) y en
     * segundo lugar por el nombre.
     * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes
     * máximas de los diferentes campos a presentar y compensa mediante la inclusión
     * de espacios en blanco.
     * La salida debe quedar tabulada como sigue:
     * <p>
     * Producto Precio Fabricante
     * -----------------------------------------------------
     * GeForce GTX 1080 Xtreme|611.5500000000001 |Crucial
     * Portátil Yoga 520 |452.79 |Lenovo
     * Portátil Ideapd 320 |359.64000000000004|Lenovo
     * Monitor 27 LED Full HD |199.25190000000003|Asus
     *
     */
    @Test
    void test27() {
        var listProds = prodRepo.findAll();

        long maxNombre = listProds.stream().mapToLong(p -> p.getNombre().length()).max().orElse(0);

        long maxPrecio = listProds.stream()
                .mapToLong(p -> BigDecimal.valueOf(p.getPrecio())
                        .setScale(2, RoundingMode.HALF_UP).toString().length())
                .max()
                .orElse(0);


        String cuerpoTabla = listProds.stream()

                .filter(p -> p.getPrecio() >= 180)
                .sorted(comparing((Producto p) -> p.getPrecio(), reverseOrder())
                        .thenComparing((Producto p) -> p.getNombre()))

                .map(p -> p.getNombre()
                        + " ".repeat((int) maxNombre - p.getNombre().length()) //Rellena con espacios en blanco
                        + "| "
                        + BigDecimal.valueOf(p.getPrecio()).setScale(2, RoundingMode.HALF_UP)
                        + " | "
                        + p.getFabricante().getNombre())

                .collect(joining("\n"));

        System.out.println(cuerpoTabla);
        // TODO
    }

    /**
     * 28. Devuelve un listado de los nombres fabricantes que existen en la base de
     * datos, junto con los nombres productos que tiene cada uno de ellos.
     * El listado deberá mostrar también aquellos fabricantes que no tienen
     * productos asociados.
     * SÓLO SE PUEDEN UTILIZAR STREAM, NO PUEDE HABER BUCLES
     * La salida debe queda como sigue:
     * Fabricante: Asus
     * <p>
     * Productos:
     * Monitor 27 LED Full HD
     * Monitor 24 LED Full HD
     * <p>
     * Fabricante: Lenovo
     * <p>
     * Productos:
     * Portátil Ideapd 320
     * Portátil Yoga 520
     * <p>
     * Fabricante: Hewlett-Packard
     * <p>
     * Productos:
     * Impresora HP Deskjet 3720
     * Impresora HP Laserjet Pro M26nw
     * <p>
     * Fabricante: Samsung
     * <p>
     * Productos:
     * Disco SSD 1 TB
     * <p>
     * Fabricante: Seagate
     * <p>
     * Productos:
     * Disco duro SATA3 1TB
     * <p>
     * Fabricante: Crucial
     * <p>
     * Productos:
     * GeForce GTX 1080 Xtreme
     * Memoria RAM DDR4 8GB
     * <p>
     * Fabricante: Gigabyte
     * <p>
     * Productos:
     * GeForce GTX 1050Ti
     * <p>
     * Fabricante: Huawei
     * <p>
     * Productos:
     * <p>
     * <p>
     * Fabricante: Xiaomi
     * <p>
     * Productos:
     *
     */
    @Test
    void test28() {
        var listFabs = fabRepo.findAll();
        var listaProductosPorFabricante = listFabs.stream()
                .map(f -> "Fabricante: " + f.getNombre() + "\n"
                        + "Productos: \n"
                        + (f.getProductos() == null || f.getProductos().isEmpty() ? ""
                        : f.getProductos().stream()
                        .map(p -> p.getNombre())
                        .collect(joining("\n"))))
                .collect(joining("\n\n"));
        System.out.println(listaProductosPorFabricante);

        // TODO
    }

    /**
     * 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no
     * tienen ningún producto asociado.
     */
    @Test
    void test29() {
        var listFabs = fabRepo.findAll();
        var listaSinProductos = listFabs.stream()
                .filter(f -> f.getProductos() == null || f.getProductos().isEmpty())
                .map(Fabricante::getNombre)
                .toList();
        listaSinProductos.forEach(x -> System.out.println(x));
        Assertions.assertEquals(2, listaSinProductos.size());
        // TODO
    }

    /**
     * 30. Calcula el número total de productos que hay en la tabla productos.
     * Utiliza la api de stream.
     */
    @Test
    void test30() {
        var listProds = prodRepo.findAll();
        long count = listProds.stream().count();
        System.out.println("Número total de productos: " + count);
        Assertions.assertEquals(9, count);
        // TODO
    }

    /**
     * 31. Calcula el número de fabricantes con productos, utilizando un stream de
     * Productos.
     */
    @Test
    void test31() {
        var listProds = prodRepo.findAll();
        long count = listProds.stream()
                .map(p -> p.getFabricante().getCodigo())
                .distinct()
                .count();
        System.out.println("Número de fabricantes con productos: " + count);
        Assertions.assertEquals(7, count);
        // TODO
    }

    /**
     * 32. Calcula la media del precio de todos los productos
     */
    @Test
    void test32() {
        var listProds = prodRepo.findAll();
        double average = listProds.stream()
                .mapToDouble(Producto::getPrecio)
                .average()
                .orElse(0.0);
        System.out.println("Media del precio de todos los productos: " + average);
        Assertions.assertEquals(262.5433333333333, average);
        // TODO
    }

    /**
     * 33. Calcula el precio más barato de todos los productos. No se puede utilizar
     * ordenación de stream.
     */
    @Test
    void test33() {
        var listProds = prodRepo.findAll();
        double min = listProds.stream()
                .mapToDouble(Producto::getPrecio)
                .min()
                .orElse(0.0);
        System.out.println("Precio más barato de todos los productos: " + min);
        Assertions.assertEquals(99.99, min);
        // TODO
    }

    /**
     * 34. Calcula la suma de los precios de todos los productos.
     */
    @Test
    void test34() {
        var listProds = prodRepo.findAll();
        double sum = listProds.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();
        System.out.println("Suma de los precios de todos los productos: " + sum);
        Assertions.assertEquals(2362.89, sum);
        // TODO
    }

    /**
     * 35. Calcula el número de productos que tiene el fabricante Asus.
     */
    @Test
    void test35() {
        var listProds = prodRepo.findAll();
        long count = listProds.stream()
                .filter(p -> p.getFabricante().getNombre().equals("Asus"))
                .count();
        System.out.println("Número de productos que tiene el fabricante Asus: " + count);
        Assertions.assertEquals(2, count);
        // TODO
    }

    /**
     * 36. Calcula la media del precio de todos los productos del fabricante Asus.
     */
    @Test
    void test36() {
        var listProds = prodRepo.findAll();
        double average = listProds.stream()
                .filter(p -> p.getFabricante().getNombre().equals("Asus"))
                .mapToDouble(Producto::getPrecio)
                .average()
                .orElse(0.0);
        System.out.println("Media del precio de todos los productos del fabricante Asus: " + average);
        Assertions.assertEquals(159.62095, average);
        // TODO
    }

    /**
     * 37. Muestra el precio máximo, precio mínimo, precio medio y el número total
     * de productos que tiene el fabricante Crucial.
     * Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como
     * "acumulador".
     */
    @Test
    void test37() {
        var listProds = prodRepo.findAll();

        var summaryStadictics = listProds.stream()
                .filter(p -> p.getFabricante().getNombre().equals("Crucial"))
                .mapToDouble(p -> p.getPrecio())
                .summaryStatistics();

        System.out.println(summaryStadictics);

        Double[] reduced = listProds.stream()
                .filter(producto -> producto.getFabricante().getNombre().equals("Crucial"))
                .map(producto -> new Double[]{producto.getPrecio(), producto.getPrecio(), producto.getPrecio(), 0.0})
                .reduce(new Double[]{Double.MAX_VALUE, 0.0, 0.0, 0.0, 0.0}, (a, b) -> {
                    double minAct = 0.0;
                    double maxAct = 0.0;
                    double sumAct = 0.0;
                    double countAct = 0.0;

                    double minAnt = (Double) a[0];
                    if ((Double) b[0] < minAnt) {
                        minAct = (Double) b[0];
                    } else {
                        minAct = minAnt;
                    }

                    double maxAnt = (Double) a[1];
                    if ((Double) b[1] > maxAnt) {
                        maxAct = (Double) b[1];
                    }
                    double sumAnt = (Double) a[2];
                    sumAct = sumAnt + b[2];

                    double countAnt = (Double) a[3];
                    countAnt = countAnt + b[3];

                    return new Double[]{minAct, maxAct, sumAct, countAct};
                });
        System.out.println(Arrays.toString(reduced));
        // TODO
    }

    /**
     * 38. Muestra el número total de productos que tiene cada uno de los
     * fabricantes.
     * El listado también debe incluir los fabricantes que no tienen ningún
     * producto.
     * El resultado mostrará dos columnas, una con el nombre del fabricante y otra
     * con el número de productos que tiene.
     * Ordene el resultado descendentemente por el número de productos. Utiliza
     * String.format para la alineación de los nombres y las cantidades.
     * La salida debe queda como sigue:
     * <p>
     * Fabricante #Productos
     * -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
     * Asus 2
     * Lenovo 2
     * Hewlett-Packard 2
     * Samsung 1
     * Seagate 1
     * Crucial 2
     * Gigabyte 1
     * Huawei 0
     * Xiaomi 0
     *
     */
    @Test
    void test38() {
        var listFabs = fabRepo.findAll();
        // TODO

    }

    /**
     * 39. Muestra el precio máximo, precio mínimo y precio medio de los productos
     * de cada uno de los fabricantes.
     * El resultado mostrará el nombre del fabricante junto con los datos que se
     * solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[]
     * como "acumulador".
     * Deben aparecer los fabricantes que no tienen productos.
     */
    @Test
    void test39() {
        var listFabs = fabRepo.findAll();

        // TODO
    }

    /**
     * 40. Muestra el precio máximo, precio mínimo, precio medio y el número total
     * de productos de los fabricantes que tienen un precio medio superior a 200€.
     * No es necesario mostrar el nombre del fabricante, con el código del
     * fabricante es suficiente.
     */
    @Test
    void test40() {
            var listFabs = fabRepo.findAll();

            var fabricantesFiltrados = listFabs.stream()
                    .map(fabricante -> {
                        var estadisticas = fabricante.getProductos()
                                .stream()
                                .mapToDouble(Producto::getPrecio)
                                .summaryStatistics();

                        return new Object[]{fabricante.getCodigo(), estadisticas};
                    })
                    .filter(obj -> {
                        var stats = (DoubleSummaryStatistics) obj[1];
                        return stats.getAverage() > 200;
                    })
                    .toList();

            fabricantesFiltrados.forEach(obj -> {
                int codigo = (int) obj[0];
                var stats = (DoubleSummaryStatistics) obj[1];
                System.out.println("Fabricante código: " + codigo);
                System.out.println("  Precio máximo: " + stats.getMax());
                System.out.println("  Precio mínimo: " + stats.getMin());
                System.out.println("  Precio medio: " + stats.getAverage());
                System.out.println("  Total productos: " + stats.getCount());
                System.out.println("------------------------------------");
            });

            // Assertions (puedes ajustar los valores si sabes los datos exactos del repositorio)
            Assertions.assertFalse(fabricantesFiltrados.isEmpty(),
                    "Debe haber al menos un fabricante con precio medio > 200€");

            Assertions.assertTrue(
                    fabricantesFiltrados.stream()
                            .allMatch(obj -> ((DoubleSummaryStatistics) obj[1]).getAverage() > 200),
                    "Todos los fabricantes filtrados deben tener media > 200€"
            );
        }


        // TODO


 /**
      * 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más
      * productos.
      */
     @Test
     void test41() {
         var listFabs = fabRepo.findAll();

         var fabricantesConDosOMas = listFabs.stream()
                 .filter(f -> f.getProductos() != null && f.getProductos().size() >= 2)
                 .map(Fabricante::getNombre)
                 .toList();

         fabricantesConDosOMas.forEach(System.out::println);

         Assertions.assertEquals(4, fabricantesConDosOMas.size());
     }

    /**
     * 42. Devuelve un listado con los nombres de los fabricantes y el número de
     * productos que tiene cada uno con un precio superior o igual a 220 €.
     * Ordenado de mayor a menor número de productos.
     */
    @Test
    void test42() {
        var listFabs = fabRepo.findAll();

        var resultado = listFabs.stream()
                .map(f -> new java.util.AbstractMap.SimpleEntry<>(
                        f.getNombre(),
                        f.getProductos() == null ? 0L
                                : f.getProductos().stream().filter(p -> p.getPrecio() >= 220).count()
                ))
                .sorted(java.util.Comparator.comparingLong((java.util.AbstractMap.SimpleEntry<String, Long> e) -> e.getValue()).reversed())
                .map(e -> e.getKey() + " " + e.getValue())
                .toList();

        resultado.forEach(System.out::println);

        Assertions.assertEquals(listFabs.size(), resultado.size());
    }

    /**
     * 43.Devuelve un listado con los nombres de los fabricantes donde la suma del
     * precio de todos sus productos es superior a 1000 €
     */
    @Test
    void test43() {
        var listFabs = fabRepo.findAll();
        var listaFabs = listFabs.stream()
                .filter(f -> f.getProductos() != null &&
                        f.getProductos().stream().mapToDouble(Producto::getPrecio).sum() > 1000)
                .map(Fabricante::getNombre)
                .toList();
        listaFabs.forEach(x -> System.out.println(x));
        Assertions.assertEquals(3, listaFabs.size());
        // TODO
    }

    /**
     * 44. Devuelve un listado con los nombres de los fabricantes donde la suma del
     * precio de todos sus productos es superior a 1000 €
     * Ordenado de menor a mayor por cuantía de precio de los productos.
     */
    @Test
    void test44() {
        var listFabs = fabRepo.findAll();
        var listaFabs = listFabs.stream()
                .filter(f -> f.getProductos() != null &&
                        f.getProductos().stream().mapToDouble(Producto::getPrecio).sum() > 1000)
                .sorted(comparingDouble(f -> f.getProductos().stream().mapToDouble(Producto::getPrecio).sum()))
                .map(Fabricante::getNombre)
                .toList();
        listaFabs.forEach(x -> System.out.println(x));
        // TODO
    }

    /**
     * 45. Devuelve un listado con el nombre del producto más caro que tiene cada
     * fabricante.
     * El resultado debe tener tres columnas: nombre del producto, precio y nombre
     * del fabricante.
     * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el
     * nombre del fabricante.
     */
    @Test
    void test45() {
        var listFabs = fabRepo.findAll();
        var listaProductosMasCaros = listFabs.stream()
                .filter(f -> f.getProductos() != null && !f.getProductos().isEmpty())
                .map(f -> {
                    Producto productoMasCaro = f.getProductos().stream()
                            .max(comparingDouble(Producto::getPrecio))
                            .orElse(null);
                    return new Object[]{productoMasCaro.getNombre(), productoMasCaro.getPrecio(), f.getNombre()};
                })
                .sorted(comparing(o -> (String) o[2]))
                .toList();

        // TODO
    }

    /**
     * 46. Devuelve un listado de todos los productos que tienen un precio mayor o
     * igual a la media de todos los productos de su mismo fabricante.
     * Se ordenará por fabricante en orden alfabético ascendente y los productos de
     * cada fabricante tendrán que estar ordenados por precio descendente.
     */
    @Test
    void test46() {
        var listFabs = fabRepo.findAll();
        double mediaProductos = listFabs.stream().flatMap(fabricante -> fabricante.getProductos().stream())
                .mapToDouble(p -> p.getPrecio()).average()
                .orElse(0.0);
        double sumaTotal = listFabs.stream().flatMap(fabricante -> fabricante.getProductos().stream())
                .mapToDouble(p -> p.getPrecio()).reduce(0.0, (a, b) -> a + b);

        long totalProductos = listFabs.stream().flatMap(fabricante -> fabricante.getProductos().stream())
                .count();

        double media = sumaTotal / totalProductos;

        System.out.println("Media: " + media + " Media por stream: " + mediaProductos);


        // TODO
    }

    @Test
    void testFlatmapPrevio() {


        String[] words = new String[]{"Hello", "World"};

        List<String[]> list = stream(words)
                .map(word -> word.split(""))

                .peek(strings -> {
                            System.out.println(strings);

                            System.out.println(Arrays.toString(strings));

                        }
                )

                //Aplica a cada palabra del array, pero word.split devuelve un array de String, de modo que
                // map ha transformado el flujo de Stream<String> a Stream<String[]>
                .distinct()
                .toList();


    }

    @Test
    void testFlatmapAlternativaMap() {


        String[] words = new String[]{"Hello", "World"};

        var list = stream(words)
                .map(word -> word.split(""))
                .map(strings -> stream(strings))

                .peek(strings -> {
                            System.out.println(strings);

                            //System.out.println(Arrays.toString(strings));

                        }
                )


                //Aplica a cada palabra del array, pero word.split devuelve un array de String, de modo que
                // map ha transformado el flujo de Stream<String> a Stream<String[]>
                .distinct()
                .toList();


    }

    @Test
    void testFlatmapSolucion() {


        String[] words = new String[]{"Hello", "World"};

        var list = stream(words)
                .map(word -> word.split(""))
                .flatMap(strings -> stream(strings))

                //.peek(strings ->   System.out.println(strings) )


                //Aplica a cada palabra del array, pero word.split devuelve un array de String, de modo que
                // map ha transformado el flujo de Stream<String> a Stream<String[]>
                .distinct()
                .peek(strings -> System.out.println(strings))
                .toList();


    }

    @Test
    void testMatch() {


        String[] words = new String[]{"Hello", "World"};

        Assertions.assertTrue(stream(words)
                .map(word -> word.split(""))
                .flatMap(strings -> stream(strings))
                .distinct()
                .anyMatch(s -> "l".equals(s)));

        Assertions.assertTrue(stream(words)
                .map(word -> word.split(""))
                .flatMap(strings -> stream(strings))
                .distinct()
                .noneMatch(s -> "z".equals(s)));

        Assertions.assertTrue(stream(words)
                .map(word -> word.split(""))
                .flatMap(strings -> stream(strings))
                .distinct()
                .allMatch(s -> s.length() == 1));

    }
}
