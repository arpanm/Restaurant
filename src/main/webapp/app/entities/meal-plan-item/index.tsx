import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MealPlanItem from './meal-plan-item';
import MealPlanItemDetail from './meal-plan-item-detail';
import MealPlanItemUpdate from './meal-plan-item-update';
import MealPlanItemDeleteDialog from './meal-plan-item-delete-dialog';

const MealPlanItemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MealPlanItem />} />
    <Route path="new" element={<MealPlanItemUpdate />} />
    <Route path=":id">
      <Route index element={<MealPlanItemDetail />} />
      <Route path="edit" element={<MealPlanItemUpdate />} />
      <Route path="delete" element={<MealPlanItemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MealPlanItemRoutes;
