import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PincodeMaster from './pincode-master';
import Location from './location';
import ApplicationUser from './application-user';
import Otp from './otp';
import Restaurant from './restaurant';
import Banner from './banner';
import FoodCategory from './food-category';
import Price from './price';
import QtyUnit from './qty-unit';
import Quantity from './quantity';
import Nutrition from './nutrition';
import IngredienceMaster from './ingredience-master';
import Item from './item';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="pincode-master/*" element={<PincodeMaster />} />
        <Route path="location/*" element={<Location />} />
        <Route path="application-user/*" element={<ApplicationUser />} />
        <Route path="otp/*" element={<Otp />} />
        <Route path="restaurant/*" element={<Restaurant />} />
        <Route path="banner/*" element={<Banner />} />
        <Route path="food-category/*" element={<FoodCategory />} />
        <Route path="price/*" element={<Price />} />
        <Route path="qty-unit/*" element={<QtyUnit />} />
        <Route path="quantity/*" element={<Quantity />} />
        <Route path="nutrition/*" element={<Nutrition />} />
        <Route path="ingredience-master/*" element={<IngredienceMaster />} />
        <Route path="item/*" element={<Item />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
