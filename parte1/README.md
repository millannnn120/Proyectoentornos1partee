Sistema de Gestion de Pedidos
Proyecto de Programacion Orientada a Objetos que implementa un sistema comercial para gestionar clientes, productos y pedidos.
Descripcion
Este proyecto aplica los principios de POO mediante herencia, encapsulacion y relaciones entre clases. El sistema permite gestionar dos tipos de productos (fisicos y digitales), registrar clientes y crear pedidos con calculo automatico de precios.
Estructura del Proyecto
src/
    modelo/
        Producto.java (clase abstracta)
        ProductoFisico.java
        ProductoDigital.java
        Cliente.java
        Pedido.java
    principal/
        Main.java

diagramas/
    DiagramaUML_Inicial.puml
    DiagramaUML_Inicial.png
    DiagramaUML_Final.puml
    DiagramaUML_Final.png

Caracteristicas Principales

Productos Fisicos: Incluyen coste de envio (5 euros) y aplican IVA del 21%
Productos Digitales: Aplican IVA del 21% con descuento del 15%
Gestion de Clientes: Almacena nombre, correo y direccion
Pedidos: Asocia cliente con productos y calcula total automaticamente

Como Ejecutar
Requisitos

Java JDK 8 o superior
IDE (Eclipse, IntelliJ IDEA, Visual studio) o compilador Java en terminal

Desde IDE

Importa el proyecto en tu IDE
Ejecuta la clase Main.java del paquete principal


# Ejecutar
Diagramas UML
Los diagramas estan en formato PlantUML (.puml). Para visualizarlos:

Usa el plugin de PlantUML o visita PlantUML Online

Relaciones Implementadas

Herencia: Producto -> ProductoFisico, ProductoDigital
Asociacion: Pedido -> Cliente (1 a 1)
Agregacion: Pedido -> Producto (1 a N)

Ejemplo de Salida
SISTEMA DE GESTION DE PEDIDOS

Producto agregado: Teclado Mecanico RGB
Producto agregado: Monitor 27 pulgadas
Producto agregado: Microsoft Office 365


RESUMEN DEL PEDIDO 1

Cliente: Carlos Martinez
Correo: carlos.martinez@email.com
Direccion: Calle Mayor 15, Madrid

Productos:

- Teclado Mecanico RGB - Precio base: 79.99 euros (Fisico) - Envio: 5.0 euros - Total: 101.79 euros
- Monitor 27 pulgadas - Precio base: 249.99 euros (Fisico) - Envio: 8.5 euros - Total: 310.99 euros
- Microsoft Office 365 - Precio base: 69.99 euros (Digital) - Tamano: 250 MB - Total: 71.94 euros

TOTAL DEL PEDIDO: 484.72 euros

Autor
Javier Millán Rodríguez 30/11/2025


