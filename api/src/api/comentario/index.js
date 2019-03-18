import { Router } from 'express'
import { middleware as query } from '../../services/querymen'
import { middleware as body } from 'bodymen'
import { create, index, show, update, destroy, destroyAdmin } from './controller'
import { schema } from './model'
import { token, master } from '../../services/passport'
export Comentario, { schema } from './model'

const router = new Router()
const { autor, contenido } = schema.tree

/**
 * @api {post} /comentarios Create comentario
 * @apiName CreateComentario
 * @apiGroup Comentario
 * @apiParam autor Comentario's autor.
 * @apiParam contenido Comentario's contenido.
 * @apiSuccess {Object} comentario Comentario's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Comentario not found.
 */
router.post('/:noticia',
  token({ required: true }),
  body({ autor, contenido }),
  create)

/**
 * @api {get} /comentarios Retrieve comentarios
 * @apiName RetrieveComentarios
 * @apiGroup Comentario
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of comentarios.
 * @apiSuccess {Object[]} rows List of comentarios.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  token({ required: true }),
  query(),
  index)

/**
 * @api {get} /comentarios/:id Retrieve comentario
 * @apiName RetrieveComentario
 * @apiGroup Comentario
 * @apiSuccess {Object} comentario Comentario's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Comentario not found.
 */
router.get('/:id',
  token({ required: true }),
  show)

/**
 * @api {put} /comentarios/:id Update comentario
 * @apiName UpdateComentario
 * @apiGroup Comentario
 * @apiParam autor Comentario's autor.
 * @apiParam contenido Comentario's contenido.
 * @apiSuccess {Object} comentario Comentario's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Comentario not found.
 */
router.put('/:id',
  token({ required: true }),
  body({ autor, contenido }),
  update)

/**
 * @api {delete} /comentarios/:id Delete comentario
 * @apiName DeleteComentario
 * @apiGroup Comentario
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Comentario not found.
 */
router.delete('/:id',
  token({ required: true }),
  destroy)

router.delete('/admin/:id',
  token({ required: true, roles: ['admin'] }),
  destroyAdmin)

export default router
