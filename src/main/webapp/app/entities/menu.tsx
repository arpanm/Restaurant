import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/pincode-master">
        <Translate contentKey="global.menu.entities.pincodeMaster" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/location">
        <Translate contentKey="global.menu.entities.location" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/application-user">
        <Translate contentKey="global.menu.entities.applicationUser" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/otp">
        <Translate contentKey="global.menu.entities.otp" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/restaurant">
        <Translate contentKey="global.menu.entities.restaurant" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/banner">
        <Translate contentKey="global.menu.entities.banner" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/food-category">
        <Translate contentKey="global.menu.entities.foodCategory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/price">
        <Translate contentKey="global.menu.entities.price" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/qty-unit">
        <Translate contentKey="global.menu.entities.qtyUnit" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/quantity">
        <Translate contentKey="global.menu.entities.quantity" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/nutrition">
        <Translate contentKey="global.menu.entities.nutrition" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/ingredience-master">
        <Translate contentKey="global.menu.entities.ingredienceMaster" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/item">
        <Translate contentKey="global.menu.entities.item" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/weight-info">
        <Translate contentKey="global.menu.entities.weightInfo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/calorie-info">
        <Translate contentKey="global.menu.entities.calorieInfo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/meal-plan-settings">
        <Translate contentKey="global.menu.entities.mealPlanSettings" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/avoid">
        <Translate contentKey="global.menu.entities.avoid" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/meal-plan">
        <Translate contentKey="global.menu.entities.mealPlan" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/meal-plan-item">
        <Translate contentKey="global.menu.entities.mealPlanItem" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/skip-date">
        <Translate contentKey="global.menu.entities.skipDate" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
