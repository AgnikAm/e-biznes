describe('Koszyk', () => {
    beforeEach(() => {
      cy.visit('http://localhost:3000/');
      cy.get('[data-testid="product-add"]').as('addButtons');
    });
  
    it('dodaje produkt do koszyka i wyświetla go', () => {
      cy.get('@addButtons').first().click();
      //cy.get('[data-testid="cart-item"]', { timeout: 5000 }).should('exist');
      //cy.get('[data-testid="cart-item"]').should('have.length', 1);
      cy.get('[data-testid="cart-total"]').should('contain.text', 'zł');
    });
  
    it('dodaje kilka produktów i pokazuje poprawną sumę', () => {
      cy.get('@addButtons').eq(0).click();
      cy.get('@addButtons').eq(1).click();
      //cy.get('[data-testid="cart-item"]').should('have.length', 2);
  
      let prices: number[] = [];
  
      /*cy.get('[data-testid="cart-item"] span').each(($el) => {
        const text = $el.text();
        const match = text.match(/(\d+(?:[.,]\d+)?)\s*zł/);
        if (match) {
          prices.push(parseFloat(match[1].replace(',', '.')));
        }
      });*/
  
      cy.get('[data-testid="cart-total"]').invoke('text').then((totalText) => {
        const total = parseFloat(totalText.replace(/[^\d,]/g, '').replace(',', '.'));
        const expected = prices.reduce((a, b) => a + b, 0);
        expect(total).to.be.closeTo(expected, 0.01);
      });
    });
  
    it('usuwa produkt z koszyka', () => {
      cy.get('@addButtons').first().click();
      cy.get('[data-testid="cart-remove"]').click();
      //cy.get('[data-testid="cart-item"]').should('not.exist');
      cy.get('[data-testid="cart-empty"]').should('contain', 'Koszyk jest pusty');
    });
  
    it('wyświetla pusty koszyk po załadowaniu', () => {
      //cy.get('[data-testid="cart-item"]').should('not.exist');
      cy.get('[data-testid="cart-empty"]').should('exist');
    });
  });
  