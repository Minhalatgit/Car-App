const Joi = require('joi');

const loginSchema = Joi.object().keys({
    email: Joi.string().required().email().messages({
        "any.required": "Email is empty",
        "string.email": "Email is not valid"
    }),
    password: Joi.string().required().min(6).messages({
        "any.required": "Password is empty",
        "string.min": "Password is too short"
    })
});

const registerSchema = Joi.object().keys({
    email: Joi.string().required().email().messages({
        "any.required": "Email is empty",
        "string.email": "Email is not valid"
    }),
    password: Joi.string().required().min(6).messages({
        "any.required": "Password is empty",
        "string.min": "Password is too short"
    }),
    confirm_password: Joi.string().required().min(6).valid(Joi.ref('password')).messages({
        "any.required": "Confirm password is empty",
        "string.min": "Confirm password is too short"
    })
});

module.exports = {
    loginSchema,
    registerSchema
}