var carouselWidth = $('.carousel-inner')[0].scrollWidth;
var cardWidth = $('.carousel-item').width();

var scrollPosition = 0;

$('.carousel-control-next').on('click', function(){
    scrollPosition = scrollPosition + cardWidth;
    $('carousel-inner').animate({scrollLeft: scrollPosition},600);
});
