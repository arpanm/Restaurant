import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FoodCategory from './food-category';
import FoodCategoryDetail from './food-category-detail';
import FoodCategoryUpdate from './food-category-update';
import FoodCategoryDeleteDialog from './food-category-delete-dialog';

const FoodCategoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FoodCategory />} />
    <Route path="new" element={<FoodCategoryUpdate />} />
    <Route path=":id">
      <Route index element={<FoodCategoryDetail />} />
      <Route path="edit" element={<FoodCategoryUpdate />} />
      <Route path="delete" element={<FoodCategoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FoodCategoryRoutes;
