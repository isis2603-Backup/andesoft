/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function(ng)
{
var mod = ng.module("AppHotel");
mod.service('hotelSVC', [function ()
   {
       var hoteles = [];
       var logueado=false;

       this.darLog= function()
        {
          // console.log("hola"+logueado);
           return this.logueado;
       };

       this.cambiarLog=function(){
           this.logueado=true;
          console.log("hola"+this.logueado);
       };

       this.logout=function(){
           this.logueado=false;
          console.log("hola"+this.logueado);
       };

       this.agregarHotel = function(hotel)
       {
           console.log("entra a metodo agregar hotel Servicio");

           hoteles.push(hotel);
       };

       this.borrarHotel = function(hotel)
       {
             var indice = hoteles.indexOf(hotel);
            hoteles.splice(indice,1);
       };

       this.darHoteles= function()
        {
           return hoteles;
       };

   }
    ]);
})(window.angular);
