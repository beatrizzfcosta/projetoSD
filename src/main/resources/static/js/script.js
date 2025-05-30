document.addEventListener('DOMContentLoaded', function() {
    const carousel = document.querySelector('.carousel-items');
    const indicators = document.querySelectorAll('.indicator');
    let currentIndex = 0;
    const totalSlides = indicators.length;

    // Função para atualizar o carrossel
    function updateCarousel() {
        carousel.style.transform = `translateX(-${currentIndex * 25}%)`;
        
        // Atualiza os indicadores
        indicators.forEach((indicator, index) => {
            if (index === currentIndex) {
                indicator.classList.add('active');
            } else {
                indicator.classList.remove('active');
            }
        });
    }

    // Adiciona eventos de clique aos indicadores
    indicators.forEach((indicator, index) => {
        indicator.addEventListener('click', () => {
            currentIndex = index;
            updateCarousel();
        });
    });

    // Função para avançar automaticamente
    function nextSlide() {
        currentIndex = (currentIndex + 1) % totalSlides;
        updateCarousel();
    }

    // Inicia o carrossel automático
    let interval = setInterval(nextSlide, 5000);

    // Pausa o carrossel quando o mouse está sobre ele
    carousel.addEventListener('mouseenter', () => {
        clearInterval(interval);
    });

    // Retoma o carrossel quando o mouse sai
    carousel.addEventListener('mouseleave', () => {
        interval = setInterval(nextSlide, 5000);
    });

    // Inicializa o carrossel
    updateCarousel();

    const navbar = document.querySelector('.navbar');
    
    window.addEventListener('scroll', function() {
        if (window.scrollY > 50) {
            navbar.classList.add('scrolled');
        } else {
            navbar.classList.remove('scrolled');
        }
    });
}); 